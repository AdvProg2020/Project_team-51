package model.Sorts;

import model.Product;

import java.util.ArrayList;

public interface Sort {
    public ArrayList<Product> applyAscendingSort(ArrayList<Product> products);
    public ArrayList<Product> applyDescendingSort(ArrayList<Product> products);
}
