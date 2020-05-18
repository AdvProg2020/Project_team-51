package view;

import control.Controller;
import control.Filters.Filter;
import control.ProductController;
import model.FilterTypes;

public class FilterMenu extends Menu {

    public FilterMenu(Menu parentMenu) {
        super("Filter Menu", parentMenu);
        subMenus.put(1, new Menu("Show Available Filters", this) {
            @Override
            public void executeMenu() {
                showAvailableFilters();
            }
        });
        subMenus.put(1, new Menu("Apply Filter", this) {
            @Override
            public void executeMenu() {
                applyFilter();
            }
        });
        subMenus.put(1, new Menu("Current Filters", this) {
            @Override
            public void executeMenu() {
                currentFilters();
            }
        });
        subMenus.put(4, new Menu("Show Available Filters", this) {
            @Override
            public void executeMenu() {
                disableFilter();
            }
        });
    }

    @Override
    public void showMenu() {
        System.out.println("1. Show Available Filters");
        System.out.println("2. Filter");
        System.out.println("3. Current Filters");
        System.out.println("4. Disable Filter");
        System.out.println("5. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("6. Login");
        else
            System.out.println("6. Logout");
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        int size = subMenus.size();
        int option = getOptionWithRange(1, size);

        if (option <= size) {
            var nextMenu = subMenus.get(option);
            nextMenu.showMenu();
            nextMenu.executeMenu();
        } else if (option == size + 1) {
            back();
        } else if (option == size + 2) {
            if (Controller.getCurrentAccount() == null) {
                var login = new LoginMenu(this);
                login.showMenu();
                login.executeMenu();
            } else {
                logout();
                var mainMenu = new MainMenu();
                mainMenu.showMenu();
                mainMenu.executeMenu();
            }
        }
        this.executeMenu();
    }

    private void showAvailableFilters() {
        var availableFilters = ProductController.showAvailableFilters();
        if (availableFilters != null)
            for (String availableFilter : availableFilters) {
                System.out.println(availableFilter);
            }

    }

    private void applyFilter() {
        String filterName = inputInFormat("Please Enter A Valid Field Name", "(?i)(category|availability|" +
                "priceRange|rateRange)");
        var filterType = Filter.getFilterType(filterName);
        ProductController.addAppliedFilters(filterType);
    }

    private void currentFilters() {
        var currentFilters = ProductController.getAppliedFilters();
        for (FilterTypes filter : currentFilters) {
            System.out.println(filter.getFilter().getName());
        }
    }

    private void disableFilter() {
        String filterName = inputInFormat("Please Enter A Valid Field Name", "(?i)(category|availability|" +
                "priceRange|rateRange)");
        var filterType = Filter.getFilterType(filterName);
        ProductController.disableFilter(filterType);
    }
}
