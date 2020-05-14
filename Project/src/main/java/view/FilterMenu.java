package view;

import control.ProductController;
import model.FilterTypes;

public class FilterMenu extends Menu {

    public FilterMenu(Menu parentMenu) {
        super("Filter Menu", parentMenu);
    }

    @Override
    public void showMenu() {
        System.out.println("1. Show Available Filters");
        System.out.println("2. Filter [Available]");
        System.out.println("3. Current Filters");
        System.out.println("4. Disable Filter [Available]");
        System.out.println("5. Back");
    }

    @Override
    public void executeMenu() {
        command = inputInFormat("Please Enter A Valid Command" , "(?i)(show\\s+available\\s+filters|" +
                "filter\\s+(w+)|current\\s+filters|disable\\s+filter\\s+(w+)|back)");
        if (command.matches("(?i)show\\s+available\\s+filters")){
            showAvailableFilters();
        } else if (command.matches("(?i)filter\\s+(w+)")){
            applyFilter(command.split("\\s+")[1]);
        } else if (command.matches("(?i)current\\s+filters")){
            currentFilters();
        } else if (command.matches("(?i)disable\\s+filter\\s+(w+)")){
            disableFilter(command.split("\\s+")[2]);
        } else if (command.matches("(?i)back")){
            back();
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
