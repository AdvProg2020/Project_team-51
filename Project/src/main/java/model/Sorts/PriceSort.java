package model.Sorts;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class PriceSort implements Sort {

    private static PriceSort instance = null ;

    private PriceSort() {
    }

    @Override
    public ArrayList<Product> applyAscendingSort(ArrayList<Product> products) {
        products.sort(Comparator.comparing(Product::getPrice).reversed());
        return products;
    }

    @Override
    public ArrayList<Product> applyDescendingSort(ArrayList<Product> products) {
        products.sort(Comparator.comparing(Product::getPrice));
        return products;
    }

    public static PriceSort getInstance() {
        if (instance==null)
            instance = new PriceSort();
        return instance;
    }
}
