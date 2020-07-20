package Server;

import message.Message;
import model.Database.Build;
import model.Database.StatusUpdater;

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
        server.start();
    }

    private void start() {
        new Build().run();
        Thread statusUpdaterThread = new Thread(new StatusUpdater());
        statusUpdaterThread.start();

        new Thread(() -> {
            serverPrint("Server Thread:sending messages is started...");
            while (true) {
                Message message;
                synchronized (sendingMessages) {
                    message = sendingMessages.poll();
                }
                if (message != null) {
                    ClientPortal.getInstance().sendMessage(message.getReceiver(), message.toJson());
                    System.out.println("TO:" + message.getReceiver() + ":  " + message.toJson());//TODO:remove
                } else {
                    try {
                        synchronized (sendingMessages) {
                            sendingMessages.wait();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
        new Thread(() -> {
            serverPrint("Server Thread:receiving messages is started...");
            while (true) {
                Message message;
                synchronized (receivingMessages) {
                    message = receivingMessages.poll();
                }
                if (message != null) {
                    System.out.println("From:" + message.getSender() + "    " + message.toJson());//TODO:remove
                    receiveMessage(message);
                } else {
                    try {
                        synchronized (receivingMessages) {
                            receivingMessages.wait();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
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

    private void receiveMessage(Message message) {
        if (message == null) {
            // It's a null message
        }
        if (!message.getReceiver().equals(serverName)) {
            // message doesn't belong to this sever
        }
        switch (message.getMessageType()) {
            case LOGIN:
                // TODO
                break;
            case LOGOUT:
                // TODO
                break;
            case REGISTER_CUSTOMER:
                // TODO
                break;
            case REGISTER_MANAGER:
                // TODO
                break;
            case REGISTER_MANAGER_BY_MANAGER:
                // TODO
                break;
            case REGISTER_SERVICE_BY_MANAGER:
                // TODO
                break;
            case ACCEPT_ADD_AUCTION_REQUEST:
                // TODO
                break;
            case ACCEPT_ADD_COMMENT_REQUEST:
                // TODO
                break;
            case ACCEPT_ADD_ITEM_REQUEST:
                // TODO
                break;
            case ACCEPT_ADD_SELLER_FOR_ITEM_REQUEST:
                // TODO
                break;
            case ACCEPT_ADD_SELLER_REQUEST:
                // TODO
                break;
            case ACCEPT_EDIT_AUCTION_REQUEST:
                // TODO
                break;
            case ACCEPT_EDIT_PRODUCT_REQUEST:
                // TODO
                break;
            case CREATE_ADD_AUCTION_REQUEST:
                // TODO
                break;
            case CREATE_ADD_COMMENT_REQUEST:
                // TODO
                break;
            case CREATE_ADD_ITEM_REQUEST:
                // TODO
                break;
            case CREATE_ADD_SELLER_FOR_ITEM_REQUEST:
                // TODO
                break;
            case CREATE_ADD_SELLER_REQUEST:
                // TODO
                break;
            case CREATE_EDIT_AUCTION_REQUEST:
                // TODO
                break;
            case CREATE_EDIT_PRODUCT_REQUEST:
                // TODO
                break;
            case CHARGE_WALLET:
                // TODO
                break;
            case CREATE_BID:
                // TODO
                break;
            case CREATE_CATEGORY:
                // TODO
                break;
            case CREATE_FILE_FOR_SALE:
                // TODO
                break;
            case CREATE_OFF_CODE:
                // TODO
                break;
            case ADD_TO_CART:
                // TODO
                break;
            case APPLY_OFF_CODE:
                // TODO
                break;
            case BUY_FILE:
                // TODO
                break;
            case DECREMENT_PRODUCT_QUANTITY:
                // TODO
                break;
            case INCREMENT_PRODUCT_QUANTITY:
                // TODO
                break;
            case REMOVE_PRODUCT_FROM_CART:
                // TODO
                break;
            case SET_WAGE:
                // TODO
                break;
            case TAKE_FROM_WALLET:
                // TODO
                break;
            case PAY_CART_VIA_BANK:
                // TODO
                break;
            case PAY_CART_VIA_WALLET:
                // TODO
                break;
        }

    }

}
