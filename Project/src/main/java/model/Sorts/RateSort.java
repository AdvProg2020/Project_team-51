package model.Sorts;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class RateSort extends Sort {

    private static RateSort instance = null ;

    private RateSort() {
        name = "Rate" ;
        isAscending = false ;
    }

    protected ArrayList<Product> applyAscendingSort(ArrayList<Product> products) {
        isAscending = true;
        products.sort(Comparator.comparing(Product::averageRate).reversed());
        return products;
    }

    protected ArrayList<Product> applyDescendingSort(ArrayList<Product> products) {
        isAscending = false ;
        products.sort(Comparator.comparing(Product::averageRate));
        return products;
    }

    public static RateSort getInstance() {
        if (instance==null)
            instance=new RateSort();
        return instance;
    }

    @Override
    public ArrayList<Product> applySort(ArrayList<Product> products , Boolean isAscending) {
        return isAscending ? applyAscendingSort(products) : applyDescendingSort(products);
    }

}
