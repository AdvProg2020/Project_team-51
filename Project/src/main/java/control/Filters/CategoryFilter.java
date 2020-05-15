package control.Filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CategoryFilter extends Filter{

    private static CategoryFilter instance = null;

    private CategoryFilter(){
        name = "Category" ;
    }
    @Override
    public ArrayList<Product> applyFilter(ArrayList<Product> products, ArrayList<Category> acceptedCategories) {
        return new ArrayList<>(products.stream().filter(product -> acceptedCategories.contains(product.getParentCategory()))
                .collect(Collectors.toList()));
    }

    public static CategoryFilter getInstance() {
        if(instance==null)
            instance=new CategoryFilter();
        return instance;
    }
}
