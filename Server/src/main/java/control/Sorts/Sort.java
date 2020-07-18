package control.Sorts;

import model.Product;
import model.SortTypes;

import java.util.List;

public abstract class Sort {

    protected String name;
    protected Boolean isAscending;

    public static SortTypes getSortType(String sortName) throws IllegalArgumentException {

        if (sortName.equalsIgnoreCase("name")) {
            return SortTypes.NAME_SORT;
        } else if (sortName.equalsIgnoreCase("rate")) {
            return SortTypes.RATE_SORT;
        } else if (sortName.equalsIgnoreCase("price")) {
            return SortTypes.PRICE_SORT;
        } else if (sortName.equalsIgnoreCase("view")) {
            return SortTypes.VIEW_SORT;
        } else {
            throw new IllegalArgumentException("No such sort type exists ! ");
        }

    }

    public String getName() {
        return name;
    }

    public Boolean getAscending() {
        return isAscending;
    }

    public abstract List<Product> applySort(List<Product> products, Boolean isAscending);
}
