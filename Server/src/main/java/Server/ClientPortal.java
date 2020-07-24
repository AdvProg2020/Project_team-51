package Server;

import control.DataController;
import message.Message;
import message.MessageType;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientPortal extends Thread {

    private static final int DEFAULT_PORT = 8888;
    private static final int MAXIMUM_BRUTE_FORCE_ATTACKS = 10;
    private static final int MAXIMUM_INPUT_SIZE = 50_000_000; // Characters
    private static final int MAXIMUM_REQUEST_TIME = 20; // Seconds
    private static final int SECONDS_TO_REMAIN_ON_BLACKLIST = 120;
    private static final int MAXIMUM_REQUESTS_PER_SECOND = 10;
    private static ClientPortal ourInstance;
    private HashMap<String, Formatter> clients = new HashMap<>();
    private HashMap<String, RSAPublicKey> publicKeyHashMap = new HashMap<>();
    private HashMap<String, SecretKey> symmetricKeyMap = new HashMap<>();
    private HashMap<String, String> authToken = new HashMap<>(); //ClientName -> token
    private HashMap<String, LocalDateTime> connectionTime = new HashMap<>();
    private HashMap<String, AtomicInteger> numberOfRequests = new HashMap<>();
    private HashMap<String, AtomicInteger> failedTries = new HashMap<>();
    private HashSet<String> blackList = new HashSet<>();

    private ClientPortal() {
    }

    public static ClientPortal getInstance() {
        if (ourInstance == null)
            ourInstance = new ClientPortal();
        return ourInstance;
    }

    @Override
    public void run() {
        Server.getInstance().serverPrint("Starting ClientPortal...");
        try {
            ServerSocket serverSocket = makeServerSocket();
            while (true) {
                Socket socket = serverSocket.accept();
                Server.getInstance().serverPrint(socket.getPort() + " :   Socket PORT");
                ClientListener clientListener = new ClientListener(socket);
                clientListener.setDaemon(true);
                clientListener.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Server.getInstance().serverPrint("Error Making ServerSocket!");
            System.exit(-1);
        }
    }

    synchronized public boolean hasThisClient(String clientName) {
        return clients.containsKey(clientName);
    }

    synchronized void addClient(String name, Formatter formatter) {//TODO:add remove client
        clients.put(name, formatter);
        DataController.getInstance().putClient(name, null);
    }

    synchronized void addClientKey(String clientName, RSAPublicKey client) {
        publicKeyHashMap.put(clientName, client);
    }

    synchronized void addClientToken(String clientName, String token) {
        authToken.put(clientName, token);
        numberOfRequests.put(clientName, new AtomicInteger(0));
        failedTries.put(clientName, new AtomicInteger(0));
    }


    synchronized void addClientSymmetricKeyAndSendToClient(String clientName) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();
        Base64.getEncoder().encode(secKey.getEncoded());
        symmetricKeyMap.put(clientName, secKey);
        String encodedKey = Base64.getEncoder().encodeToString(secKey.getEncoded());
        sendMessageWithRSA(clientName, encodedKey);
    }

    public PublicKey getKeyPair(String clientName) {
        return publicKeyHashMap.get(clientName);
    }

    void addMessage(String clientName, String encryptedMessage) throws Exception {
        String message = Server.decryptSymmetric(encryptedMessage, symmetricKeyMap.get(clientName));
        if (validate(clientName, message)) {
            Server.getInstance().addToReceivingMessages(Message.convertJsonToMessage(message));
            numberOfRequests.replace(clientName, new AtomicInteger(numberOfRequests.get(clientName).incrementAndGet()));
            Message temp = JsonConverter.fromJson(message, Message.class);
            if (temp.getMessageType().equals(MessageType.LOGIN))
                failedTries.replace(clientName, new AtomicInteger(failedTries.get(clientName).incrementAndGet()));
        }
    }

    synchronized public void sendMessage(String clientName, String message) throws Exception {//TODO:Change Synchronization
        if (clients.containsKey(clientName)) {
            SecretKey key = symmetricKeyMap.get(clientName);
            clients.get(clientName).format(Server.encryptSymmetric(message, key) + "\n");
            clients.get(clientName).flush();
        } else {
            Server.getInstance().serverPrint("Client Not Found!");
        }
    }

    synchronized public void sendMessageWithRSA(String clientName, String message) throws Exception {//TODO:Change Synchronization
        if (clients.containsKey(clientName)) {
            PublicKey key = publicKeyHashMap.get(clientName);
            clients.get(clientName).format(Server.encrypt(message, key) + "\n");
            clients.get(clientName).flush();
        } else {
            Server.getInstance().serverPrint("Client Not Found!");
        }
    }

    public Set<Map.Entry<String, Formatter>> getClients() {
        return Collections.unmodifiableSet(clients.entrySet());
    }

    void removeClient(String clientName) {
        clients.remove(clientName);
    }

    private ServerSocket makeServerSocket() throws IOException {
        return new ServerSocket(DEFAULT_PORT);
    }

    private boolean validate(String client, String message) {
        return !(blackList.contains(client)) && checkReplayAttacks(message) && checkBruteForce(client) &&
                checkImproperInputs(message) && checkDenialOfService(client);
    }

    private boolean checkBruteForce(String client) {
        AtomicInteger numberOfAttempt = failedTries.get(client);
        if (numberOfAttempt == null || numberOfAttempt.get() <= MAXIMUM_BRUTE_FORCE_ATTACKS)
            return true;
        else {
            blackList.add(client);
            // Removing Client Name From Blacklist After 2 Minutes
            new Thread(() -> {
                try {
                    sleep(SECONDS_TO_REMAIN_ON_BLACKLIST * 1000);
                    blackList.remove(client);
                    failedTries.replace(client, new AtomicInteger(0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return false;
        }
    }

    private boolean checkReplayAttacks(String message) {
        Message receivedMessage = JsonConverter.fromJson(message, Message.class);
        long seconds = Duration.between(receivedMessage.getDate(), LocalDateTime.now()).toSeconds();
        return receivedMessage.getDate() != null && seconds <= MAXIMUM_REQUEST_TIME;
    }

    private boolean checkImproperInputs(String message) {
        return message.length() < MAXIMUM_INPUT_SIZE && message.startsWith("{") && message.endsWith("}");
    }

    private boolean checkDenialOfService(String client) {
        LocalDateTime firstConnection = connectionTime.get(client);
        if (firstConnection == null) return false;
        if (numberOfRequests.get(client) == null) return true;
        int requests = numberOfRequests.get(client).get();
        long seconds = Duration.between(firstConnection, LocalDateTime.now()).toSeconds();
        return (requests / (seconds + 1)) <= MAXIMUM_REQUESTS_PER_SECOND;
    }

    public void setConnectionTime(String client) {
        connectionTime.put(client, LocalDateTime.now());
    }

    public String getSecretKeyEncoded(String client) {
        return Base64.getEncoder().encodeToString(symmetricKeyMap.get(client).getEncoded());
    }


}
