package view;

public enum MenusPattern {

    SORT(menuPatternBuilder(AllCommands.SHOW_AVAILABLE_SORTS, AllCommands.SORT, AllCommands.CURRENT_SORT, AllCommands.DISABLE_SORT)),
    PRODUCT(menuPatternBuilder(AllCommands.DIGEST, AllCommands.ATTRIBUTES, AllCommands.COMPARE, AllCommands.COMMENTS
            , AllCommands.ADD_COMMENT, AllCommands.ADD_TO_CART)),
    PRODUCTS(menuPatternBuilder(AllCommands.SHOW_PRODUCT, AllCommands.SORTING, AllCommands.FILTERING)),
    FILTER(menuPatternBuilder(AllCommands.FILTER, AllCommands.CURRENT_FILTERS, AllCommands.DISABLE_FILTER, AllCommands.SHOW_AVAILABLE_FILTERS)),
    ADD_COMMENT(menuPatternBuilder(AllCommands.ADD_COMMENT)),
    AUCTION(menuPatternBuilder(AllCommands.SHOW_PRODUCT, AllCommands.OFFS)),
    LOGIN(menuPatternBuilder(AllCommands.LOGIN, AllCommands.SIGN_UP)),
    SELLER(menuPatternBuilder(AllCommands.PERSONAL_INFO, AllCommands.COMPANY_INFO, AllCommands.MANAGE_PRODUCTS, AllCommands.SALES_HISTORY,
            AllCommands.BALANCE, AllCommands.SHOW_CATEGORIES, AllCommands.VIEW_OFFS, AllCommands.ADD_PRODUCT, AllCommands.REMOVE_PRODUCT)),
    SELLER_PERSONAL_INFO(menuPatternBuilder(AllCommands.EDIT_PERSONAL_INFO)),
    EDIT_SELLER_PERSONAL_INFO(menuPatternBuilder(AllCommands.FIRST_NAME, AllCommands.LAST_NAME, AllCommands.EMAIL, AllCommands.PHONE, AllCommands.BRAND)),
    MANAGE_PRODUCTS(menuPatternBuilder(AllCommands.VIEW_PID, AllCommands.VIEW_BUYERS_PID, AllCommands.EDIT_PID));
    private String regex;

    MenusPattern(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    private static String menuPatternBuilder(AllCommands... patterns) {
        StringBuilder regex = new StringBuilder("(");
        for (AllCommands pattern : patterns) {
            regex.append(pattern.getRegex()).append("|");
        }
        regex.append("(?i)back|(?i)login|(?i)logout)");

        return regex.toString();
    }
}
