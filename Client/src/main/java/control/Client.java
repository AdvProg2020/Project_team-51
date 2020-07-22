package control;

import com.gilecode.yagson.com.google.gson.Gson;
import javafx.application.Platform;
import message.Message;
import model.People.Account;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.LinkedList;


public class Client {

    private static Client client;
    private final LinkedList<Message> sendingMessages = new LinkedList<>();
    private String clientName;
    private Account account;
    private LinkedList<Message> receivingMessages = new LinkedList<>();
    private boolean validation = true;
    private String errorMessage;
    private Socket socket;
    private Gson gson = new Gson();
    private Thread sendMessageThread;
    private Thread receiveMessageThread;
    private BufferedReader bufferedReader;
    private static KeyPair keyPair;
    private RSAPublicKey server;
    private String authToken;

    private Client() {
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }


    private void connect() throws Exception {
        keyPair = generateKeyPair();
        socket = getSocketReady();
        sendClientNameToServer(socket);
        Thread.sleep(1000);
        exchangePublicKeys(socket);
        sendMessageThread = new Thread(() -> {
            try {
                sendMessages();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sendMessageThread.start();
        receiveMessages();
    }

    private void sendClientNameToServer(Socket socket) throws IOException {
        while (!bufferedReader.readLine().equals("#Listening#")) ;
        System.out.println("server is listening to me");
        clientName = InetAddress.getLocalHost().getHostName();
        System.out.println("Client name  : " + clientName);
        socket.getOutputStream().write(("#" + clientName + "#\n").getBytes());
        int x = 1;
        String finalClientName = clientName;
        while (!bufferedReader.readLine().equals("#Valid#")) {
            x++;
            finalClientName = clientName + x;
            socket.getOutputStream().write(("#" + finalClientName + "#\n").getBytes());
        }
        clientName = finalClientName;
        System.out.println("server accepted me.");
        String encryptedToken;
        while ((encryptedToken = bufferedReader.readLine()) == null) ;
        authToken = encryptedToken;
    }

    private void exchangePublicKeys(Socket socket) throws IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        String serverPublicKey;
        while ((serverPublicKey = bufferedReader.readLine()) == null) ;
        String[] Parts = serverPublicKey.split("\\|");
        RSAPublicKeySpec Spec = new RSAPublicKeySpec(
                new BigInteger(Parts[0]),
                new BigInteger(Parts[1]));
        server = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(Spec);
        String myPublicKey = ((RSAPublicKey) keyPair.getPublic()).getModulus().toString() + "|" +
                ((RSAPublicKey) keyPair.getPublic()).getPublicExponent().toString();
        socket.getOutputStream().write((myPublicKey + "\n").getBytes());
    }

    private Socket getSocketReady() throws IOException {
        Socket socket = makeSocket();
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("network connected.");
        return socket;
    }

    private Socket makeSocket() throws IOException {
        return new Socket("localhost", 8888);
    }

    void addToSendingMessagesAndSend(Message message) {
        synchronized (sendingMessages) {
            sendingMessages.add(message);
            sendingMessages.notify();
        }
    }

    private void sendMessages() throws Exception {
        System.out.println("sending messages started");
        while (true) {
            Message message;
            synchronized (sendingMessages) {
                message = sendingMessages.poll();
            }
            if (message != null) {
                String json = message.toJson();
                socket.getOutputStream().write((encrypt(json, server) + "\n").getBytes());
                System.out.println("message sent: " + json);
            } else {
                try {
                    synchronized (sendingMessages) {
                        sendingMessages.wait();
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private void receiveMessages() throws Exception {
        System.out.println("receiving messages started.");
        while (true) {
            String json = bufferedReader.readLine();
            Message message = gson.fromJson(decrypt(json), Message.class);
            System.out.println("message received: " + decrypt(json));
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
        //TODO
    }

    private void showOrSaveMessage(Message message) {
//        if (message.getChatMessage().getReceiverUsername() == null) {
//            MainMenuController.getInstance().addChatMessage(message.getChatMessage());
//        }
    }

    private void showError(Message message) {
        validation = false;
        errorMessage = message.getExceptionMessage().getExceptionString();
        Platform.runLater(() -> System.out.println("TODO")); //TODO
    }


    private void disconnected() {
    }

    public String getClientName() {
        return clientName;
    }

    public Account getAccount() {
        return account;
    }

    void close() {
        try {
            if (socket != null) {
                socket.close();
                System.out.println("socket closed");
            }
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeConnection() {
        new Thread(() -> {
            try {
                connect();
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() ->
                        System.err.println("Connection Failed!")
                );
                disconnected();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String getAuthToken() {
        return authToken;
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public static String decrypt(String cipherText) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return new String(decryptCipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }
}
