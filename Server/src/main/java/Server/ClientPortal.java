package Server;

import control.DataController;
import message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.*;

import static Server.Server.decryptMessage;

public class ClientPortal extends Thread {

    private static final int DEFAULT_PORT = 8888;
    private static ClientPortal ourInstance;
    private HashMap<String, Formatter> clients = new HashMap<>();
    private HashMap<String, KeyPair> keyPairHashMap = new HashMap<>();

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

    synchronized void addClientKey(String clientName) {
        KeyPair pair = null;
        try {
            pair = Server.generateKeyPair();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        keyPairHashMap.put(clientName, pair);
    }


    public KeyPair getKeyPair(String clientName) {
        return keyPairHashMap.get(clientName);
    }

    void addMessage(String clientName, String message) {
        Server.getInstance().addToReceivingMessages(Message.convertJsonToMessage(decryptMessage(message)));
    }

    synchronized public void sendMessage(String clientName, String message) throws Exception {//TODO:Change Synchronization
        if (clients.containsKey(clientName)) {
            KeyPair pair = keyPairHashMap.get(clientName);
            clients.get(clientName).format(Server.encryptMessage(message) + "\n");
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
}
