package view;

import control.ProductController;
import model.SortTypes;
import control.Sorts.Sort;

import java.util.Arrays;

public class SortMenu extends Menu {

    public SortMenu(Menu parentMenu) {
        super("Sort Menu", parentMenu);
    }

    @Override
    public void showMenu() {
        System.out.println("1. Show Available Sorts");
        System.out.println("2. Sort [Available]");
        System.out.println("3. Current Sort");
        System.out.println("4. Disable Sort");
        System.out.println("5. Back");
    }

    @Override
    public void executeMenu() {
        command = inputInFormat("Please Enter A Valid Command" , "(?i)(show\\s+available\\s+sorts|" +
                "sort\\s+(w+)|current\\s+sort|disable\\s+sort|back)");
        if (command.matches("(?i)show\\s+available\\s+sorts")){
            showAvailableSorts();
        } else if (command.matches("(?i)sort\\s+(w+)")){
            sort(command.split("\\s+")[1]);
        } else if (command.matches("(?i)current\\s+sort")){
            currentSort();
        } else if (command.matches("(?i)disable\\s+sort")){
            disableSort();
        } else if (command.matches("(?i)back")){
            back();
        }
        this.executeMenu();
    }

    public void showAvailableSorts() {
        Arrays.stream(SortTypes.values()).map(SortTypes::getSort).map(Sort::getName).forEach(System.out::println);
    }

    private void sort(String sort) {
        ProductController.applySort(sort);
    }

    private void currentSort() {
        var sort = ProductController.getCurrentSort().getSort();
        System.out.println(sort.getName() + " → "  + ( sort.getAscending() ? "Ascending ↑" : "Descending ↓"));
    }

    private void disableSort() {
        ProductController.disableSort();
    }
}
