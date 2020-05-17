package view;

import control.ProductController;
import model.FilterTypes;

public class FilterMenu extends Menu {

    public FilterMenu(Menu parentMenu) {
        super("Filter Menu", parentMenu);
    }
    @Override
    public void showMenu() {
        System.out.println("- Show Available Filters");
        System.out.println("- Filter [Available]");
        System.out.println("- Current Filters");
        System.out.println("- Disable Filter [Available]");
    }
    @Override
    public void executeMenu() {
        menusHistory.push(this);
        command = inputInFormat("Please Enter A Valid Command" , MenusPattern.FILTER.getRegex());
        if (command.matches(AllCommands.SHOW_AVAILABLE_FILTERS.getRegex())) {
            showAvailableFilters();
        } else if (command.matches(AllCommands.FILTER.getRegex())) {
            applyFilter(command.split("\\s+")[1]);
        } else if (command.matches(AllCommands.CURRENT_FILTERS.getRegex())) {
            currentFilters();
        } else if (command.matches(AllCommands.DISABLE_FILTER.getRegex())) {
            disableFilter(command.split("\\s+")[2]);
        } else if (command.matches(AllCommands.BACK.getRegex())) {
            back();
        } else if (command.matches(AllCommands.LOGIN.getRegex())) {
            login();
        } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
            logout();
        }
        this.executeMenu();
    }

    private void showAvailableFilters() {

        var availableFilters = ProductController.showAvailableFilters();
        if (availableFilters!=null)
        for (String availableFilter : availableFilters) {
            System.out.println(availableFilter);
        }

    }

    private void applyFilter(String filterType) {
        ProductController.applyFilter(filterType);
    }

    private void currentFilters() {
        var currentFilters = ProductController.getAppliedFilters();
        for (FilterTypes filter : currentFilters) {
            System.out.println(filter.getFilter().getName());
        }
    }

    private void disableFilter(String filter) {
        ProductController.disableFilter(filter);
    }
}
