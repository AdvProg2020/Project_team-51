package Server;

import java.util.HashMap;
import java.util.Map;

public class DNS {

    private static Map<String, Integer> clientsPorts = new HashMap<>(); // Username --> Client Port
    private static DNS instance;

    private DNS() {
    }

    public static DNS getInstance() {
        if (instance == null)
            instance = new DNS();
        return instance;
    }


    public int getPortByUsername(String username) {
        return clientsPorts.get(username);
    }

    public void putClient(String username, int port) {
        clientsPorts.put(username, port);
    }

    public void remove(String username) {
        clientsPorts.remove(username);
    }

}
