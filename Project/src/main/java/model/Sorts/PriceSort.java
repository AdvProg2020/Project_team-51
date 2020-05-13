package model.Sorts;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class PriceSort extends Sort {

    private static PriceSort instance = null ;

    private PriceSort() {
        isAscending = false;
        name="Price";
    }


    public ArrayList<Product> applyAscendingSort(ArrayList<Product> products) {
        isAscending = true;
        products.sort(Comparator.comparing(Product::getPrice).reversed());
        return products;
    }

    public ArrayList<Product> applyDescendingSort(ArrayList<Product> products) {
        isAscending = false;
        products.sort(Comparator.comparing(Product::getPrice));
        return products;
    }

    public static PriceSort getInstance() {
        if (instance==null)
            instance = new PriceSort();
        return instance;
    }

    @Override
    public ArrayList<Product> applySort(ArrayList<Product> products, Boolean isAscending) {
        return isAscending ? applyAscendingSort(products) : applyDescendingSort(products);
    }
}
