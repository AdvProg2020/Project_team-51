package Server;

import message.Message;

import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class ClientListener extends Thread {

    private Socket socket;

    public ClientListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Scanner scanner;
        Formatter formatter;
        String name = null;
        try {
            Server.getInstance().serverPrint("New Socket Is Accepted!");
            scanner = new Scanner(socket.getInputStream());
            formatter = new Formatter(socket.getOutputStream());
            formatter.format("#Listening#\n");
            formatter.flush();
            while (true) {
                name = scanner.nextLine().split("#")[1];
                if (name.length() >= 3 && !ClientPortal.getInstance().hasThisClient(name)) {
                    ClientPortal.getInstance().addClient(name, formatter);
                    ClientPortal.getInstance().addClientKey(name);
                    formatter.format("#Valid#\n");
                    formatter.flush();
                    break;
                } else {
                    formatter.format("#InValid#\n");
                    formatter.flush();
                }
            }

            Server.getInstance().serverPrint("New Client Is Accepted!");
            DNS.getInstance().putClient(name, socket.getPort());


            Thread.sleep(3000);
            ClientPortal.getInstance().sendMessage(name, JsonConverter.toJson(new Message(name)));

            while (true) {
                String message = scanner.nextLine();
                ClientPortal.getInstance().addMessage(name, Server.decryptMessage(message));
            }
        } catch (Exception e) {

        }
    }
}