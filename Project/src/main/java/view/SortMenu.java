package view;

import control.Controller;
import control.ProductController;
import control.Sorts.Sort;
import model.SortTypes;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;

import java.util.Arrays;

public class SortMenu extends Menu {

    public SortMenu(Menu parentMenu) {
        super("Sort Menu", parentMenu);
        subMenus.put(1, new Menu("Show Available Sorts ", this) {
            @Override
            public void executeMenu() {
                showAvailableSorts();
            }
        });
        subMenus.put(2, new Menu("Show Available Sorts ", this) {
            @Override
            public void executeMenu() {
                sort();
            }
        });
        subMenus.put(3, new Menu("Show Available Sorts ", this) {
            @Override
            public void executeMenu() {
                currentSort();
            }
        });
        subMenus.put(4, new Menu("Show Available Sorts ", this) {
            @Override
            public void executeMenu() {
                disableSort();
            }
        });
    }

    @Override
    public void showMenu() {
        System.out.println("1. Show Available Sorts");
        System.out.println("2. Sort");
        System.out.println("3. Current Sort");
        System.out.println("4. Disable Sort");
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
                var login = new LoginAndRegisterMenu(this);
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

    public void showAvailableSorts() {
        Arrays.stream(SortTypes.values()).map(SortTypes::getSort).map(Sort::getName).forEach(System.out::println);
    }

    private void sort() {
        String sortName = inputInFormat("Enter A Valid Sort Name: ", "(?i)(rate|name|view|price)");
        var sort = Sort.getSortType(sortName);
        ProductController.applySort(sort);
    }

    private void currentSort() {
        var sort = ProductController.getCurrentSort().getSort();
        System.out.println(sort.getName() + " → " + (sort.getAscending() ? "Ascending ↑" : "Descending ↓"));
    }

    private void disableSort() {
        ProductController.disableSort();
    }


}
