package Server;

import control.TokenGenerator;

import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
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
                    DNS.getInstance().putClient(name, socket.getPort());
                    formatter.format("#Valid#\n");
                    formatter.flush();
                    break;
                } else {
                    formatter.format("#InValid#\n");
                    formatter.flush();
                }
            }

            // Sending Token to Client
            String clientToken = TokenGenerator.generateAuthToken();
            ClientPortal.getInstance().addClientToken(name, clientToken);
            System.out.println(clientToken);
            formatter.format(clientToken + "\n");
            formatter.flush();

            // Exchanging public keys
            //   sending server public key
            String serverPublicKey = Server.getPublicKey().getModulus().toString() + "|" +
                    Server.getPublicKey().getPublicExponent().toString();
            formatter.format(serverPublicKey + "\n");
            formatter.flush();
            //   receiving client public key
            String clientPublicKey;
            while ((clientPublicKey = scanner.nextLine()) == null) ;
            String[] Parts = clientPublicKey.split("\\|");
            RSAPublicKeySpec Spec = new RSAPublicKeySpec(new BigInteger(Parts[0]), new BigInteger(Parts[1]));
            RSAPublicKey client = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(Spec);
            ClientPortal.getInstance().addClientKey(name, client);

            Server.getInstance().serverPrint("New Client Is Accepted!");
            ClientPortal.getInstance().setConnectionTime(name);

            ClientPortal.getInstance().addClientSymmetricKeyAndSendToClient(name);

            while (true) {
                String message = scanner.nextLine();
                ClientPortal.getInstance().addMessage(name, message);
            }
        } catch (Exception e) {

        }
    }
}