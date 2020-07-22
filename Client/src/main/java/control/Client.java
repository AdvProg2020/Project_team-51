package control;

import com.gilecode.yagson.com.google.gson.Gson;
import javafx.application.Platform;
import message.Message;
import model.People.Account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyPair;
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
    private KeyPair keyPair;

    private Client() {
    }

    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
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

    private void connect() throws IOException, NullPointerException {
        socket = getSocketReady();
        sendClientNameToServer(socket);
        sendMessageThread = new Thread(() -> {
            try {
                sendMessages();
            } catch (IOException e) {
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
//        String key = null;
//        while ((key=bufferedReader.readLine())==null) ;
//        System.out.println(key);
//        keyPair = JsonConverter.fromJson(key, KeyPair.class);
//        System.out.println(key);
//        System.out.println("The public key is : " + keyPair.getPublic());
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

    private void sendMessages() throws IOException {
        System.out.println("sending messages started");
        while (true) {
            Message message;
            synchronized (sendingMessages) {
                message = sendingMessages.poll();
            }
            if (message != null) {
                String json = message.toJson();
                socket.getOutputStream().write((encryptMessage(json) + "\n").getBytes());
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

    private void receiveMessages() throws IOException {
        System.out.println("receiving messages started.");
        while (true) {
            String json = bufferedReader.readLine();
            Message message = gson.fromJson(decryptMessage(json), Message.class);
            System.out.println("message received: " + decryptMessage(json));
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
            }
        }).start();
    }
}
