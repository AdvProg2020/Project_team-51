package model.Filters;

import model.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RateRangeFilter extends Filter {
    
    private RateRangeFilter instance = null;

    private RateRangeFilter(){
        name = "RateRange" ;
    }


    @Override
    public ArrayList<Product> applyFilter(ArrayList<Product> products, Double from, Double to) {
        return new ArrayList<>(products.stream().filter(product -> (product.averageRate() >= from && product.averageRate()<= to))
                .collect(Collectors.toList()));
    }

    public RateRangeFilter getInstance() {
        if(instance==null)
            instance=new RateRangeFilter();
        return instance;
    }
}
