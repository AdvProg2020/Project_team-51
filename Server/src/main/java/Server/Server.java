package Server;

import control.DataController;
import control.Exceptions.ClientException;
import message.Message;
import model.Database.Build;
import model.Database.StatusUpdater;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
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
        return server;
    }

    public static void main(String[] args) throws Exception {
        server = new Server("Server");
        server.start();
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String encryptMessage(String plainText) {
        char[] characters = plainText.toCharArray();
        String message = "";
        for (int i = 0; i < characters.length; i++) {
            characters[i] = (char) (characters[i] + (i % 11));
            message += characters[i];
        }
        return message;
    }

    public static String decryptMessage(String plainText) {
        char[] characters = plainText.toCharArray();
        String message = "";
        for (int i = 0; i < characters.length; i++) {
            characters[i] = (char) (characters[i] - (i % 11));
            message += characters[i];
        }
        return message;
    }


    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decryptCipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    private void start() {
        new Thread(() -> new Build().run());
        Thread statusUpdaterThread = new Thread(new StatusUpdater());
        statusUpdaterThread.start();
        ClientPortal.getInstance().start();

        new Thread(() -> {
            serverPrint("Server Thread:sending messages is started...");
            while (true) {
                Message message;
                synchronized (sendingMessages) {
                    message = sendingMessages.poll();
                }
                if (message != null) {
                    try {
                        ClientPortal.getInstance().sendMessage(message.getReceiver(), message.toJson());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private void sendException(String exceptionString, String receiver) {
        addToSendingMessages(Message.makeExceptionMessage(receiver, exceptionString));
    }

    private void receiveMessage(Message message) {
        if (message == null) {
            // It's a null message
        }
        if (!message.getReceiver().equals(serverName)) {
            // message doesn't belong to this sever
        }
        try {
            switch (message.getMessageType()) {
                case LOGIN:
                    DataController.getInstance().login(message);
                    break;
                case LOGOUT:
                    DataController.getInstance().logout(message);
                    break;
                case REGISTER_CUSTOMER:
                    DataController.getInstance().registerCustomer(message);
                    break;
                case REGISTER_MANAGER:
                    DataController.getInstance().registerManager(message);
                    break;
                case REGISTER_MANAGER_BY_MANAGER:
                    DataController.getInstance().registerManagerByManager(message);
                    break;
                case REGISTER_SERVICE_BY_MANAGER:
                    DataController.getInstance().registerService(message);
                    break;
                case ACCEPT_ADD_AUCTION_REQUEST:
                    DataController.getInstance().acceptAddAuctionRequest(message);
                    break;
                case ACCEPT_ADD_COMMENT_REQUEST:
                    DataController.getInstance().acceptAddCommentRequest(message);
                    break;
                case ACCEPT_ADD_ITEM_REQUEST:
                    DataController.getInstance().acceptAddItemRequest(message);
                    break;
                case ACCEPT_ADD_SELLER_FOR_ITEM_REQUEST:
                    DataController.getInstance().acceptAddSellerForItemRequest(message);
                    break;
                case ACCEPT_ADD_SELLER_REQUEST:
                    DataController.getInstance().acceptAddSellerRequest(message);
                    break;
                case ACCEPT_EDIT_AUCTION_REQUEST:
                    DataController.getInstance().acceptEditAuctionRequest(message);
                    break;
                case ACCEPT_EDIT_PRODUCT_REQUEST:
                    DataController.getInstance().acceptEditProductRequest(message);
                    break;
                case CREATE_ADD_AUCTION_REQUEST:
                    DataController.getInstance().createAddAuctionRequest(message);
                    break;
                case CREATE_ADD_COMMENT_REQUEST:
                    DataController.getInstance().createAddCommentRequest(message);
                    break;
                case CREATE_ADD_ITEM_REQUEST:
                    DataController.getInstance().createAddItemRequest(message);
                    break;
                case CREATE_ADD_SELLER_FOR_ITEM_REQUEST:
                    DataController.getInstance().createAddSellerForItemRequest(message);
                    break;
                case CREATE_ADD_SELLER_REQUEST:
                    DataController.getInstance().createAddSellerRequest(message);
                    break;
                case CREATE_EDIT_AUCTION_REQUEST:
                    DataController.getInstance().createEditAuctionRequest(message);
                    break;
                case CREATE_EDIT_PRODUCT_REQUEST:
                    DataController.getInstance().createEditProductRequest(message);
                    break;
                case CHARGE_WALLET:
                    DataController.getInstance().chargeWallet(message);
                    break;
                case CREATE_BID:
                    DataController.getInstance().createBid(message);
                    break;
                case CREATE_CATEGORY:
                    DataController.getInstance().createCategory(message);
                    break;
                case CREATE_FILE_FOR_SALE:
                    DataController.getInstance().createFileForSale(message);
                    break;
                case CREATE_OFF_CODE:
                    DataController.getInstance().createOffCode(message);
                    break;
                case ADD_TO_CART:
                    DataController.getInstance().addToCart(message);
                    break;
                case APPLY_OFF_CODE:
                    DataController.getInstance().applyOffCode(message);
                    break;
                case BUY_FILE:
                    DataController.getInstance().buyFile(message);
                    break;
                case DECREMENT_PRODUCT_QUANTITY:
                    DataController.getInstance().decrementProductQuantity(message);
                    break;
                case INCREMENT_PRODUCT_QUANTITY:
                    DataController.getInstance().incrementProductQuantity(message);
                    break;
                case REMOVE_PRODUCT_FROM_CART:
                    DataController.getInstance().removeProductFromCart(message);
                    break;
                case SET_WAGE:
                    DataController.getInstance().setWage(message);
                    break;
                case TAKE_FROM_WALLET:
                    DataController.getInstance().takeFromWallet(message);
                    break;
                case PAY_CART_VIA_BANK:
                    DataController.getInstance().payCart(message);
                    //TODO VIA BANK
                    break;
                case PAY_CART_VIA_WALLET:
                    DataController.getInstance().payCart(message);
                    // TODO VIA WALLET
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + message.getMessageType());
            }
        }
//        catch (ServerException e) {
//            serverPrint(e.getMessage());
//            if (message != null) {
//                sendException("server has error:(", message.getSender());
//            }
//        }
        catch (ClientException e) {
            sendException(e.getMessage(), message.getSender());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
