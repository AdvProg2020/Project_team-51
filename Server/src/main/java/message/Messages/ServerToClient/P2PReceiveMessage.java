package message.Messages.ServerToClient;

public class P2PReceiveMessage {

    String encodedSecretKey;
    int port;

    public P2PReceiveMessage(String encodedSecretKey, int port) {
        this.encodedSecretKey = encodedSecretKey;
        this.port = port;
    }

    public String getEncodedSecretKey() {
        return encodedSecretKey;
    }

    public int getPort() {
        return port;
    }
}
