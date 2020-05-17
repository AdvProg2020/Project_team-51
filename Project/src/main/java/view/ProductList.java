package view;

import control.Exceptions.InvalidProductIdException;
import model.Product;
import view.Enums.AllCommands;
import view.Enums.MenusPattern;

public class ProductList extends Menu {


    public ProductList(Menu parentMenu) {
        super("Product list", parentMenu);
    }

    @Override
    public void showMenu() {
        System.out.println("- Show Product [PID]");
        System.out.println("- Sorting");
        System.out.println("- Filtering");
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        command = inputInFormat("Choose : ", MenusPattern.PRODUCTS.getRegex());
        if (command.matches(AllCommands.SHOW_PRODUCT.getRegex())) {
            showProduct(command.split("\\s")[2]);
        } else if (command.matches(AllCommands.SORTING.getRegex())) {
            var sort = new SortMenu(this);
            sort.showMenu();
            sort.executeMenu();
        } else if (command.matches(AllCommands.FILTERING.getRegex())) {
            var filter = new FilterMenu(this);
            filter.showMenu();
            filter.executeMenu();
        } else if (command.matches(AllCommands.BACK.getRegex())) {
            back();
        } else if (command.matches(AllCommands.LOGIN.getRegex())) {
            login();
        } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
            logout();
        }
    }

    private void showProduct(String pid) {
        Product product;
        try {
            product = Product.getProductById(pid);
        } catch (InvalidProductIdException e) {
            System.out.println(e.getMessage());
            return;
        }
        var productPage = new ProductPageMenu(this, product);
        productPage.showMenu();
        productPage.executeMenu();
    }

}
