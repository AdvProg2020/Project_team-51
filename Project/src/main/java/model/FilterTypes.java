package model;

import control.Filters.AvailabilityFilter;
import control.Filters.Filter;
import control.Filters.PriceRangeFilter;
import control.Filters.RateRangeFilter;

public enum FilterTypes {

    AVAILABILITY_FILTER(AvailabilityFilter.getInstance()) ,
    PRICE_RANGE_FILTER(PriceRangeFilter.getInstance()) ,
    RATE_RANGE_FILTER(RateRangeFilter.getInstance())
    ;

    private Filter filter ;

    FilterTypes(Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }
}
