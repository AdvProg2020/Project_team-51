package control.Sorts;

import model.Product;

import java.util.Comparator;
import java.util.List;

public class NameSort extends Sort {


    private static NameSort instance = null;

    private NameSort() {
        isAscending = false;
        name = "Name";
    }

    public static NameSort getInstance() {
        if (instance == null)
            instance = new NameSort();
        return instance;
    }

    public List<Product> applyAscendingSort(List<Product> products) {
        isAscending = true;
        products.sort(Comparator.comparing(Product::getName));
        return products;
    }

    public List<Product> applyDescendingSort(List<Product> products) {
        isAscending = false;
        products.sort(Comparator.comparing(Product::getName).reversed());
        return products;
    }

    @Override
    public List<Product> applySort(List<Product> products, Boolean isAscending) {
        return isAscending ? applyAscendingSort(products) : applyDescendingSort(products);
    }
}
