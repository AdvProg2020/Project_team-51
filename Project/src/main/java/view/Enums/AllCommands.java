package view.Enums;

public enum AllCommands {

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
    SIGN_UP("(?i)signUp"),
    PERSONAL_INFO("(?i)personal\\s+info"),
    COMPANY_INFO("(?i)company\\s+info"),
    SALES_HISTORY("(?i)sales\\s+history"),
    MANAGE_PRODUCTS("(?i)manage\\s+products"),
    SHOW_CATEGORIES("(?i)show\\s+categories"),
    VIEW_OFFS("(?i)view\\s+offs"),
    BALANCE("(?i)balance"),
    EDIT_PERSONAL_INFO("(?i)edit"),
    FIRST_NAME("(?i)first\\s+name"),
    LAST_NAME("(?i)last\\s+name"),
    EMAIL("(?i)email"),
    PHONE("(?i)phone"),
    BRAND("(?i)brand"),
    ADD_PRODUCT("(?i)add\\s+product"),
    REMOVE_PRODUCT("(?i)remove\\s+product"),
    VIEW_PID("(?i)view\\s+(\\w+)"),
    VIEW_BUYERS_PID("(?i)view\\s+(\\w+)"),
    EDIT_PID("(?i)edit\\s+(\\w+)"),
    ;

    private String regex;

    AllCommands(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return regex;
    }

    public String getRegex() {
        return regex;
    }
}
