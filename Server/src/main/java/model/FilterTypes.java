package model;

import control.Filters.*;

public enum FilterTypes {

    AVAILABILITY_FILTER(AvailabilityFilter.getInstance()),
    PRICE_RANGE_FILTER(PriceRangeFilter.getInstance()),
    RATE_RANGE_FILTER(RateRangeFilter.getInstance()),
    CATEGORY_FILTER(CategoryFilter.getInstance());

    private Filter filter;

    FilterTypes(Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }
}
