package control.Filters;

import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RateRangeFilter extends Filter {
    
    private static RateRangeFilter instance = null;

    private RateRangeFilter(){
        name = "RateRange" ;
    }


    @Override
    public ArrayList<Product> applyFilter(ArrayList<Product> products, Double from, Double to) {
        return new ArrayList<>(products.stream().filter(product -> (product.averageRate() >= from && product.averageRate()<= to))
                .collect(Collectors.toList()));
    }

    public static RateRangeFilter getInstance() {
        if(instance==null)
            instance=new RateRangeFilter();
        return instance;
    }
}
