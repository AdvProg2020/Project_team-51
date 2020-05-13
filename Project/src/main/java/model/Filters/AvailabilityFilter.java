package model.Filters;

import model.Product;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AvailabilityFilter implements Filter {

    private AvailabilityFilter instance = null;

    private AvailabilityFilter(){

    }

    @Override
    public ArrayList<Product> applyFilter(ArrayList<Product> products) {
        return new ArrayList<>(products.stream().filter(product -> product.getQuantity() > 0).collect(Collectors.toList()));
    }

    public AvailabilityFilter getInstance() {
        if(instance==null)
            instance=new AvailabilityFilter();
        return instance;
    }
}
