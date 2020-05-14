package control.Sorts;

import model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class NameSort extends Sort {


    private static NameSort instance = null ;

    private NameSort() {
        isAscending = false ;
        name="Name";
    }


    public ArrayList<Product> applyAscendingSort(ArrayList<Product> products) {
        isAscending=true;
        products.sort(Comparator.comparing(Product::getName).reversed());
        return products;
    }


    public ArrayList<Product> applyDescendingSort(ArrayList<Product> products) {
        isAscending=false;
        products.sort(Comparator.comparing(Product::getName));
        return products;
    }

    public static NameSort getInstance() {
        if (instance==null)
            instance=new NameSort();
        return instance;
    }

    @Override
    public ArrayList<Product> applySort(ArrayList<Product> products, Boolean isAscending) {
        return isAscending ? applyAscendingSort(products) : applyDescendingSort(products);
    }
}
