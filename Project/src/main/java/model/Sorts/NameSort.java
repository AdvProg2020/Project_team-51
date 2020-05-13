package model.Sorts;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class NameSort implements Sort {

    private static NameSort instance = null ;

    private NameSort() {
    }

    @Override
    public ArrayList<Product> applyAscendingSort(ArrayList<Product> products) {
        products.sort(Comparator.comparing(Product::getName).reversed());
        return products;
    }

    @Override
    public ArrayList<Product> applyDescendingSort(ArrayList<Product> products) {
        products.sort(Comparator.comparing(Product::getName));
        return products;
    }

    public static NameSort getInstance() {
        if (instance==null)
            instance=new NameSort();
        return instance;
    }
}
