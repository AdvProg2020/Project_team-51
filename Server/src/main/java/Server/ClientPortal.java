package Server;

import java.util.*;

public class ClientPortal extends Thread {

    private static final String CONFIG_PATH = "config";
    private static final int DEFAULT_PORT = 8888;
    private static ClientPortal ourInstance = new ClientPortal();
    private HashMap<String, Formatter> clients = new HashMap<>();

    private ClientPortal() {
    }

    public static ClientPortal getInstance() {
        return ourInstance;
    }

    @Override
    public void run() {

    }

    synchronized public boolean hasThisClient(String clientName) {
        return clients.containsKey(clientName);
    }

    synchronized void addClient(String name, Formatter formatter) {//TODO:add remove client
        clients.put(name, formatter);
//        DataCenter.getInstance().putClient(name, null);
    }

    void addMessage(String clientName, String message) {
//        Server.getInstance().addToReceivingMessages(Message.convertJsonToMessage(message));
    }

    synchronized public void sendMessage(String clientName, String message) {//TODO:Change Synchronization
        if (clients.containsKey(clientName)) {
            clients.get(clientName).format(message + "\n");
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
}
