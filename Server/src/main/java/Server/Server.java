package Server;

import control.DataController;
import control.Exceptions.ClientException;
import message.Message;
import model.Database.Build;
import model.Database.StatusUpdater;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Queue;

public class Server {

    private static Server server;
    private static KeyPair keyPair;
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


    public static RSAPublicKey getPublicKey() {
        return (RSAPublicKey) keyPair.getPublic();
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public static String encryptSymmetric(String plainText, SecretKey secretKey) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = aesCipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptSymmetric(String cipherText, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(aesCipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
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

    private void sendException(String exceptionString, String receiver) {
        addToSendingMessages(Message.makeExceptionMessage(receiver, exceptionString));
    }

    public void addToReceivingMessages(Message message) {
        synchronized (receivingMessages) {
            serverPrint("Entered here : ");
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
            System.out.println("HOY");
        }
        try {
            System.out.println(message.getMessageType());
            switch (message.getMessageType()) {
                case GIVE_DATA:
                    DataController.getInstance().giveData(message);
                    break;
                case IS_THERE_ANY_MANAGER:
                    DataController.getInstance().isThereAnyManager(message);
                    break;
                case LOGIN:
                    DataController.getInstance().login(message);
                    break;
                case LOGOUT:
                    DataController.getInstance().logout(message);
                    break;
                case P2P_SERVER:
                    DataController.getInstance().addP2PServerToDNS(message);
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
            System.out.println(e.getMessage());
            sendException(e.getMessage(), message.getSender());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void start() throws Exception {
        keyPair = generateKeyPair();
        new Thread(() -> new Build().run()).start();
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
                        System.out.println("TO:" + message.getReceiver() + ":  " + message.toJson());//TODO:remove
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        synchronized (sendingMessages) {
                            sendingMessages.wait();
                        }
                    } catch (InterruptedException ignored) {
                        System.out.println("BITCH");
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
                        System.out.println("BITCH PLEASE");
                    }
                }
            }
        }).start();
    }


}
