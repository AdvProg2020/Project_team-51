package model;

import control.Sorts.*;

public enum SortTypes {

    NAME_SORT(NameSort.getInstance()),
    PRICE_SORT(PriceSort.getInstance()),
    RATE_SORT(RateSort.getInstance()),
    VIEW_SORT(ViewSort.getInstance());


    private Sort sort;

    SortTypes(Sort sort) {
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }
}
