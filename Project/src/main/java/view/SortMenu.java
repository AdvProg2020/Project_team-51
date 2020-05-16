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
        System.out.println("- Show Available Sorts");
        System.out.println("- Sort [Available]");
        System.out.println("- Current Sort");
        System.out.println("- Disable Sort");
    }

    @Override
    public void executeMenu() {
        command = inputInFormat("Please Enter A Valid Command" , MenusPattern.SORT.getRegex());
        if (command.matches(AllPatterns.SHOW_AVAILABLE_SORTS.getRegex())){
            showAvailableSorts();
        } else if (command.matches(AllPatterns.SORT.getRegex())){
            sort(command.split("\\s+")[1]);
        } else if (command.matches(AllPatterns.CURRENT_SORT.getRegex())){
            currentSort();
        } else if (command.matches(AllPatterns.DISABLE_SORT.getRegex())){
            disableSort();
        } else if (command.matches(AllPatterns.BACK.getRegex())){
            back();
        } else if (command.matches(AllPatterns.LOGIN.getRegex())){
            login();
        } else if (command.matches(AllPatterns.LOGOUT.getRegex())){
            logout();
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
