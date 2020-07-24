package Server;

import model.Product;

import java.util.HashMap;
import java.util.Map;

public class DNS {

    private static Map<Product, Integer> clientsPorts = new HashMap<>(); // Product  --> Client P2P Server Port
    private static DNS instance;

    private DNS() {
    }

    public static DNS getInstance() {
        if (instance == null)
            instance = new DNS();
        return instance;
    }


    public int getPortByUsername(Product product) {
        return clientsPorts.get(product);
    }

    public void putClient(Product product, int port) {
        clientsPorts.put(product, port);
    }


}
