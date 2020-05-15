package control.Filters;

import model.Category;
import model.Product;

import java.util.ArrayList;

public abstract class Filter {

    protected String name;

    public ArrayList<Product> applyFilter(ArrayList<Product> products) {
        return null;
    }
    public ArrayList<Product> applyFilter(ArrayList<Product> products, Double from, Double to) {
        return null;
    }
    public ArrayList<Product> applyFilter(ArrayList<Product> products, ArrayList<Category> categories) {
        return null;
    }

    public String getName() {
        return name;
    }


}
