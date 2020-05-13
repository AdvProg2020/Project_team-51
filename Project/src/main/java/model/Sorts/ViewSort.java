package model.Sorts;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class ViewSort extends Sort {
    
    private static ViewSort instance = null ;

    private ViewSort() {
        name = "View" ;
        isAscending = false ;
    }

    protected ArrayList<Product> applyAscendingSort(ArrayList<Product> products) {
        isAscending = true;
        products.sort(Comparator.comparing(Product::getViews).reversed());
        return products;
    }

    protected ArrayList<Product> applyDescendingSort(ArrayList<Product> products) {
        isAscending = false ;
        products.sort(Comparator.comparing(Product::getViews));
        return products;
    }

    public static ViewSort getInstance() {
        if (instance==null)
            instance=new ViewSort();
        return instance;
    }
    @Override
    public ArrayList<Product> applySort(ArrayList<Product> products, Boolean isAscending) {
        return null;
    }
}
