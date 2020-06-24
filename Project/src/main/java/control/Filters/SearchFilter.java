package control.Filters;

import model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class SearchFilter extends Filter {

    private static SearchFilter instance = null;
    private String search;

    private SearchFilter() {
        name = "Search";
    }

    public static SearchFilter getInstance() {
        if (instance == null)
            instance = new SearchFilter();
        return instance;
    }

    @Override
    public List<Product> applyFilter(List<Product> products) {
        return products.stream().filter(p -> p.getName().contains(search)).collect(Collectors.toList());
    }
}
