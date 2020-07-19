package Server;

import message.Message;

import java.util.LinkedList;
import java.util.Queue;

public class Server {

    private static Server server;
    public final String serverName;

    private final Queue<Message> sendingMessages = new LinkedList<>();
    private final Queue<Message> receivingMessages = new LinkedList<>();

    private Server(String serverName) {
        this.serverName = serverName;
    }

    public static Server getInstance() {
        if (server == null)
            server = new Server("Server ");
        return server;
    }

    public static void main(String[] args) {

    }

    public void serverPrint(String string) {
        System.out.println("\u001B[32m" + string.trim() + "\u001B[0m");
    }

    public void addToSendingMessages(Message message) {
        synchronized (sendingMessages) {
            sendingMessages.add(message);
            sendingMessages.notify();
        }
    }

    public void addToReceivingMessages(Message message) {
        synchronized (receivingMessages) {
            receivingMessages.add(message);
            receivingMessages.notify();
        }
    }

}
