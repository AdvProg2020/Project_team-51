package model.Sorts;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class RateSort implements Sort {

    private static RateSort instance = null ;

    private RateSort() {
    }

    @Override
    public ArrayList<Product> applyAscendingSort(ArrayList<Product> products) {
        products.sort(Comparator.comparing(Product::averageRate).reversed());
        return products;
    }

    @Override
    public ArrayList<Product> applyDescendingSort(ArrayList<Product> products) {
        products.sort(Comparator.comparing(Product::averageRate));
        return products;
    }

    public static RateSort getInstance() {
        if (instance==null)
            instance=new RateSort();
        return instance;
    }
}
