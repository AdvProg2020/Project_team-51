package control.Filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter extends Filter {

    private static CategoryFilter instance = null;
    public List<Category> acceptedCategories = new ArrayList<>();

    private CategoryFilter() {
        name = "Category";
    }

    public static CategoryFilter getInstance() {
        if (instance == null)
            instance = new CategoryFilter();
        return instance;
    }

    @Override
    public List<Product> applyFilter(List<Product> products, List<Category> acceptedCategories) {
        this.acceptedCategories = acceptedCategories;
        return products.stream().filter(product -> acceptedCategories.contains(product.getParentCategory()))
                .collect(Collectors.toList());
    }
}
