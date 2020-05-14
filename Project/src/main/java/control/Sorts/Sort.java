package control.Sorts;

import model.Product;

import java.util.ArrayList;

public abstract class Sort {

    protected String name;
    protected Boolean isAscending ;
    public abstract ArrayList<Product> applySort(ArrayList<Product> products, Boolean isAscending);
    public String getName() {
        return name;
    }

    public Boolean getAscending() {
        return isAscending;
    }
}
