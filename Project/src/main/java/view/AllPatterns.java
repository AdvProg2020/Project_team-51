package view;

public enum AllPatterns {

    SHOW_PRODUCT("(?i)show\\s+product\\s+([0-9]+)"),
    SORTING("(?i)sorting"),
    FILTERING("(?i)filtering"),
    BACK("(?i)back"),
    LOGIN("(?i)login"),
    LOGOUT("(?i)logout"),
    SHOW_PRODUCTS("(?i)show\\s+products"),
    DIGEST("(?i)digest"),
    ATTRIBUTES("(?i)attributes"),
    COMPARE("(?i)(compare\\s+([0-9]+))"),
    COMMENTS("(?i)comments"),
    ADD_COMMENT("(?i)add\\s+comment"),
    VIEW_CATEGORIES("(?i)view\\s+categories"),
    PRODUCTS("(?i)products"),
    SHOW_AVAILABLE_FILTERS("(?i)show\\s+available\\s+filters"),
    FILTER("(?i)filter\\s+(w+)"),
    CURRENT_FILTERS("(?i)current\\s+filters"),
    DISABLE_FILTER("(?i)disable\\s+filter\\s+(w+)"),
    SHOW_AVAILABLE_SORTS("(?i)show\\s+available\\s+sorts"),
    SORT("(?i)sort\\s+(w+)"),
    CURRENT_SORT("(?i)current\\s+sort"),
    DISABLE_SORT("(?i)current\\s+sort"),
    OFFS("(?i)offs"),
    ADD_TO_CART("(?i)add\\s+to\\s+cart"),
    ;

    private String regex ;

    AllPatterns(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
