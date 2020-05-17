package control.Sorts;

import model.Product;

import java.util.Comparator;
import java.util.List;

public class RateSort extends Sort {

    private static RateSort instance = null ;

    private RateSort() {
        name = "Rate" ;
        isAscending = false ;
    }

    protected List<Product> applyAscendingSort(List<Product> products) {
        isAscending = true;
        products.sort(Comparator.comparing(Product::averageRate));
        return products;
    }

    protected List<Product> applyDescendingSort(List<Product> products) {
        isAscending = false;
        products.sort(Comparator.comparing(Product::averageRate).reversed());
        return products;
    }

    public static RateSort getInstance() {
        if (instance==null)
            instance=new RateSort();
        return instance;
    }

    @Override
    public List<Product> applySort(List<Product> products, Boolean isAscending) {
        return isAscending ? applyAscendingSort(products) : applyDescendingSort(products);
    }

}
