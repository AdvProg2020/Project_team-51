package view;

import control.Controller;
import control.Exceptions.NoCategoriesFoundException;
import control.ProductController;
import model.Category;
import model.Product;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;

import java.util.ArrayList;
import java.util.List;

public class ProductMenu extends Menu {

    private Category category;
    private List<Category> subCategories = new ArrayList<>();
    private List<Product> productsOfThisCategory = null;

    public ProductMenu(Menu parentMenu, Category category) {
        super("Products Menu", parentMenu);
        this.category = category;
        if (category != null) {
            try {
                this.subCategories = ProductController.getSubCategories(category);
                for (int i = 0; i < subCategories.size(); i++) {
                    subMenus.put((i + 1), new ProductMenu(this, subCategories.get(i)));
                }
            } catch (NoCategoriesFoundException e) {
                e.getMessage();
            }
        }
//        subMenus.put(subCategories.size() + 1,
//                new ProductListMenu(this, ProductController.showProductsOfThisCategory(category)));
    }

    @Override
    public void showMenu() {
        int size;
        try {
            size = subCategories.size();
        } catch (NullPointerException e) {
            size = 0;
        }
        viewCategories();
        System.out.println("----------------------------");
        System.out.println((size + 1) + ". Show Products");
        System.out.println((size + 2) + ". Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println((size + 3) + ". Login");
        else
            System.out.println((size + 3) + ". Logout");
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        int size;
        try {
            size = subCategories.size();
        } catch (NullPointerException e) {
            size = 0;
        }
        int option = getOptionWithRange(1, size + 1);
        if (option <= size + 1) {
            var nextMenu = subMenus.get(option);
            nextMenu.showMenu();
            nextMenu.executeMenu();
        } else if (option == size + 2) {
            back();
        } else if (option == size + 3) {
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
    }

    private void viewCategories() {
        try {
            var subCategories = ProductController.getSubCategories(category);
            listCategories(subCategories);
        } catch (NoCategoriesFoundException e) {
            e.getMessage();
        }
    }

    private void listCategories(List<Category> categories) {
        if (categories == null)
            System.out.println("There is no categories !");
        else {
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getName());
            }
        }
    }


}
