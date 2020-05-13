package model.Filters;

import model.Product;

import java.util.ArrayList;

public interface Filter {
    default public ArrayList<Product> applyFilter (ArrayList<Product> products) {
        return null;
    }
    default public ArrayList<Product> applyFilter (ArrayList<Product> products, Double from ,Double to) {
        return null;
    }
}
