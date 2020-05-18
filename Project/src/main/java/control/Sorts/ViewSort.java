package control.Sorts;

import model.Product;

import java.util.Comparator;
import java.util.List;

public class ViewSort extends Sort {

    private static ViewSort instance = null;

    private ViewSort() {
        name = "View";
        isAscending = false;
    }

    public static ViewSort getInstance() {
        if (instance == null)
            instance = new ViewSort();
        return instance;
    }

    protected List<Product> applyAscendingSort(List<Product> products) {
        isAscending = true;
        products.sort(Comparator.comparing(Product::getViews));
        return products;
    }

    protected List<Product> applyDescendingSort(List<Product> products) {
        isAscending = false;
        products.sort(Comparator.comparing(Product::getViews).reversed());
        return products;
    }

    @Override
    public List<Product> applySort(List<Product> products, Boolean isAscending) {
        return isAscending ? applyAscendingSort(products) : applyDescendingSort(products);
    }
}
