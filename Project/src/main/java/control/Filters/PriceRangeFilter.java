package control.Filters;

import model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PriceRangeFilter extends Filter {

    private static PriceRangeFilter instance = null;

    private PriceRangeFilter(){
        name = "PriceRange" ;
    }


    @Override
    public ArrayList<Product> applyFilter(ArrayList<Product> products, Double from, Double to) {
        return new ArrayList<>(products.stream().filter(product -> (product.getPrice() >= from && product.getPrice()<= to))
                .collect(Collectors.toList()));
    }

    public static PriceRangeFilter getInstance() {
        if(instance==null)
            instance=new PriceRangeFilter();
        return instance;
    }
}
