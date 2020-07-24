package control;

import com.gilecode.yagson.com.google.gson.Gson;
import javafx.application.Platform;
import message.JsonConverter;
import message.Message;
import model.Category;
import model.People.Account;
import model.Product;
import model.WriteIntoFiles;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
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
    private static KeyPair keyPair;
    private static SecretKey symmetricKey;
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

    public static String encryptSymmetric(String plainText) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
        byte[] cipherText = aesCipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptSymmetric(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, symmetricKey);
        return new String(aesCipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    private void connect() throws Exception {
        keyPair = generateKeyPair();
        socket = getSocketReady();
        sendClientNameToServer(socket);
        Thread.sleep(1000);
        exchangePublicKeys(socket);
        getSymmetricKeys();
        sendMessageThread = new Thread(() -> {
            try {
                sendMessages();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sendMessageThread.start();
        addToSendingMessagesAndSend(Message.makeGiveMeTheDataMessage("Server"));
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

    private void getSymmetricKeys() throws Exception {
        String encryptedSymmetricKey;
        while ((encryptedSymmetricKey = bufferedReader.readLine()) == null) ;
        byte[] decodedKey = Base64.getDecoder().decode(decrypt(encryptedSymmetricKey));
        symmetricKey = new SecretKeySpec(decodedKey, 0, 16, "AES");
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

    public void addToSendingMessagesAndSend(Message message) {
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
                socket.getOutputStream().write((encryptSymmetric(json) + "\n").getBytes());
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

    private void receiveMessages() throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println("receiving messages started.");
        while (true) {
            String json = bufferedReader.readLine();
            Message message = gson.fromJson(decryptSymmetric(json), Message.class);
            System.out.println("message received: " + decryptSymmetric(json));
            handleMessage(message);
        }
    }

    public static String decryptSymmetric(String cipherText, SecretKey symmetricKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, symmetricKey);
        return new String(aesCipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    private void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case IS_THERE_ANY_MANAGER:
                Controller.setIsThereAnyManager(message.getIsThereAnyManagerMessage().isThereAnyManager());
                break;
            case DATA:
                loadData(message);
                break;
            case DONE:
                System.out.println("DONE !");
                break;
            case UPDATE_ACCOUNT:
                updateAccount(message);
                break;
            case ADD_FILE_SERVER:
                runAServerForSendingFile(message.getAddP2PFileServerMessage().getProduct());
                break;
            case P2P_RECEIVE:
                receiveFileFromP2PServer(message);
                break;
        }
    }

    private void receiveFileFromP2PServer(Message message) {
        byte[] decodedKey = Base64.getDecoder().decode(message.getP2PReceiveMessage().getEncodedSecretKey());
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, 16, "AES");
        int port = message.getP2PReceiveMessage().getPort();
        Socket receiveSocket = null;
        try {
            receiveSocket = new Socket("localhost", port);
            BufferedReader input = new BufferedReader(new InputStreamReader(receiveSocket.getInputStream()));
            String file;
            while ((file = input.readLine()) == null) ;
            WriteIntoFiles.writeIntoFile(decryptSymmetric(file, secretKey), "//Client//src//main//resources//downloadedFiles");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    private void updateAccount(Message message) {
        System.out.println(message.getUpdateAccountMessage().getAccount());
        Controller.setCurrentAccount(message.getUpdateAccountMessage().getAccount());
    }

    private void loadData(Message message) {
        Category.getAllCategories().addAll(message.getDataMessage().getCategories());
        Product.getAllProducts().addAll(message.getDataMessage().getProducts());
        // Bids
        Controller.setIsThereAnyManager(message.getDataMessage().isThereAnyManager());
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

    public void runAServerForSendingFile(Product product) {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            while (true) while (true) {
                this.socket.getOutputStream().write((encryptSymmetric(JsonConverter.toJson
                        (Message.makeP2PServerPortMessage("Server", product, serverSocket.getLocalPort()))) + "\n").getBytes());
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort() + " :   Socket PORT");
                FileSender fileSender = new FileSender(socket, product.getFile());
                fileSender.setDaemon(true);
                fileSender.start();
                System.out.println(clientName + "'s Server for sending file is running ...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class FileSender extends Thread {

        Socket socket;
        File file;

        public FileSender(Socket socket, File file) {
            this.socket = socket;
            this.file = file;
        }

        @Override
        public void run() {
            try {
                socket.getOutputStream().write((encryptSymmetric(JsonConverter.toJson(file) + "\n").getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
