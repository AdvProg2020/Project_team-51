package control.Filters;

import model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class RateRangeFilter extends Filter {

    private static RateRangeFilter instance = null;
    public double from;
    public double to;

    private RateRangeFilter() {
        name = "RateRange";
    }

    public static RateRangeFilter getInstance() {
        if (instance == null)
            instance = new RateRangeFilter();
        return instance;
    }

    @Override
    public List<Product> applyFilter(List<Product> products, Double from, Double to) {
        this.from = from;
        this.to = to;
        return products.stream().filter(product -> (product.averageRate() >= from && product.averageRate() <= to))
                .collect(Collectors.toList());
    }
}
