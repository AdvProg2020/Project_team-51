package control.Filters;

import model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityFilter extends Filter {

    private static AvailabilityFilter instance = null;

    private AvailabilityFilter() {
        name = "Availability";
    }

    public static AvailabilityFilter getInstance() {
        if (instance == null)
            instance = new AvailabilityFilter();
        return instance;
    }

    @Override
    public List<Product> applyFilter(List<Product> products) {
        return products.stream().filter(product -> product.getTotalQuantity() > 0).collect(Collectors.toList());
    }
}
