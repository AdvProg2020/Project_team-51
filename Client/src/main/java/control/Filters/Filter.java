package control.Filters;

import model.Category;
import model.FilterTypes;
import model.Product;

import java.util.List;

public abstract class Filter {

    protected String name;

    public static FilterTypes getFilterType(String filterName) {

        if (filterName.equalsIgnoreCase("availability")) {
            return FilterTypes.AVAILABILITY_FILTER;
        } else if (filterName.equalsIgnoreCase("category")) {
            return FilterTypes.CATEGORY_FILTER;
        } else if (filterName.equalsIgnoreCase("price")) {
            return FilterTypes.PRICE_RANGE_FILTER;
        } else if (filterName.equalsIgnoreCase("rate")) {
            return FilterTypes.RATE_RANGE_FILTER;
        } else {
            throw new IllegalArgumentException("No such filter type exists ! ");
        }
    }

    public List<Product> applyFilter(List<Product> products) {
        return null;
    }

    public List<Product> applyFilter(List<Product> products, Double from, Double to) {
        return null;
    }

    public String getName() {
        return name;
    }

    public List<Product> applyFilter(List<Product> products, List<Category> categories) {
        return null;
    }

}
