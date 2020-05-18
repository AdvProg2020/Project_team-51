package control.Filters;

import model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class PriceRangeFilter extends Filter {

    private static PriceRangeFilter instance = null;
    public double from;
    public double to;

    private PriceRangeFilter() {
        name = "PriceRange";
    }

    public static PriceRangeFilter getInstance() {
        if (instance == null)
            instance = new PriceRangeFilter();
        return instance;
    }

    @Override
    public List<Product> applyFilter(List<Product> products, Double from, Double to) {
        this.from = from;
        this.to = to;
        return products.stream().filter(product -> (product.getAveragePrice() >= from && product.getAveragePrice() <= to))
                .collect(Collectors.toList());
    }
}
