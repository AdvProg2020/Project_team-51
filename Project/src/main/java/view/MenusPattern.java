package view;

public enum MenusPattern {

    SORT( menuPatternBuilder(AllPatterns.SHOW_AVAILABLE_SORTS,AllPatterns.SORT,AllPatterns.CURRENT_SORT,AllPatterns.DISABLE_SORT)),
    PRODUCT(menuPatternBuilder(AllPatterns.DIGEST,AllPatterns.ATTRIBUTES,AllPatterns.COMPARE,AllPatterns.COMMENTS
            ,AllPatterns.ADD_COMMENT,AllPatterns.ADD_TO_CART)),
    PRODUCTS(menuPatternBuilder(AllPatterns.SHOW_PRODUCT,AllPatterns.SORTING,AllPatterns.FILTERING)),
    FILTER(menuPatternBuilder(AllPatterns.FILTER,AllPatterns.CURRENT_FILTERS,AllPatterns.DISABLE_FILTER,AllPatterns.SHOW_AVAILABLE_FILTERS)),
    ADD_COMMENT(menuPatternBuilder(AllPatterns.ADD_COMMENT)),
    AUCTION(menuPatternBuilder(AllPatterns.SHOW_PRODUCT,AllPatterns.OFFS)),
    LOGIN(menuPatternBuilder(AllPatterns.LOGIN,AllPatterns.SIGN_UP)),
    SELLER(menuPatternBuilder(AllPatterns.PERSONAL_INFO,AllPatterns.COMPANY_INFO,AllPatterns.MANAGE_PRODUCTS,AllPatterns.SALES_HISTORY,
           AllPatterns.BALANCE,AllPatterns.SHOW_CATEGORIES,AllPatterns.VIEW_OFFS ))
    ;
    private String regex ;

    MenusPattern(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    private static String menuPatternBuilder(AllPatterns ... patterns){
        StringBuilder regex = new StringBuilder("(");
        for (AllPatterns pattern : patterns) {
            regex.append(pattern.getRegex()).append("|");
        }
        regex.append("(?i)back|(?i)login|(?i)logout)");

        return regex.toString();
    }
}
