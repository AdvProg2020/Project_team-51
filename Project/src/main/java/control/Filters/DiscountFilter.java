package control.Filters;

import model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountFilter extends Filter {

    private static DiscountFilter instance = null;

    private DiscountFilter() {
        name = "Discount";
    }

    public static DiscountFilter getInstance() {
        if (instance == null)
            instance = new DiscountFilter();
        return instance;
    }

    @Override
    public List<Product> applyFilter(List<Product> products) {
        return products.stream().filter(product -> false).collect(Collectors.toList());
    }
}
