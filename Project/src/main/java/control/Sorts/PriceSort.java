package control.Sorts;

import model.Product;

import java.util.Comparator;
import java.util.List;

public class PriceSort extends Sort {

    private static PriceSort instance = null ;

    private PriceSort() {
        isAscending = false;
        name="Price";
    }


    public List<Product> applyAscendingSort(List<Product> products) {
        isAscending = true;
        products.sort(Comparator.comparing(Product::getAveragePrice));
        return products;
    }

    public List<Product> applyDescendingSort(List<Product> products) {
        isAscending = false;
        products.sort(Comparator.comparing(Product::getAveragePrice).reversed());
        return products;
    }

    public static PriceSort getInstance() {
        if (instance==null)
            instance = new PriceSort();
        return instance;
    }

    @Override
    public List<Product> applySort(List<Product> products, Boolean isAscending) {
        return isAscending ? applyAscendingSort(products) : applyDescendingSort(products);
    }
}
