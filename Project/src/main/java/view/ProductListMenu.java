package view;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.ProductController;
import model.Auction;
import model.People.Seller;
import model.Product;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListMenu extends Menu {

    private List<Product> productsOfThisCategory = new ArrayList<>();

    public ProductListMenu(Menu parentMenu, List<Product> productsOfThisCategory) {
        super("Product list", parentMenu);
        subMenus.put(1, new Menu("Show Product By Id", this) {
            @Override
            public void executeMenu() {
                showProduct(this);
            }
        });
        subMenus.put(2, new SortMenu(this));
        subMenus.put(3, new SortMenu(this));
        this.productsOfThisCategory = productsOfThisCategory;
    }

    @Override
    public void showMenu() {
        if (parentMenu instanceof AuctionMenu) showProductsWithOffs();
        else if (parentMenu instanceof ProductMenu) listProducts();
        System.out.println("1. Show Product [PID]");
        System.out.println("2. Sorting");
        System.out.println("3. Filtering");
        System.out.println("4. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("5. Login");
        else
            System.out.println("5. Logout");

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
    }

    private void showProduct(Menu parent) {
        String pid = inputInFormat("Enter Product Id : ", "\\w+");
        Product product = null;
        try {
            product = Product.getProductById(pid);
        } catch (InvalidProductIdException e) {
            System.out.println(e.getMessage());
            showProduct(parent);
        }
        var productPage = new ProductPageMenu(parent, product);
        productPage.showMenu();
        productPage.executeMenu();
    }

    private void showProductsWithOffs() {
        var currentSort = ProductController.getCurrentSort().getSort();
        var productsOfThisCategory = currentSort.applySort(getProductsWithOffs(), currentSort.getAscending());
        for (int i = 0; i < productsOfThisCategory.size(); i++) {
            if (i != 0)
                System.out.println("--------------------------");
            var product = productsOfThisCategory.get(i);
            System.out.println((i + 1) + ". " + product.getName() + " |  " + product.getPrice() + "$" + "  |  " + product.getProductId());
        }
    }

    private ArrayList<Product> getProductsWithOffs() {

        return new ArrayList<>(Seller.getAllSellers().stream().map(Seller::getAllAuctions).collect(Collectors.toList())
                .stream().flatMap(Collection::stream).map(Auction::getAppliedProducts).flatMap(Collection::stream)
                .collect(Collectors.toList()));


    }

    private void listProducts() {
        var currentSort = ProductController.getCurrentSort().getSort();
        var productsOfThisCategory = currentSort.applySort(this.productsOfThisCategory, currentSort.getAscending());
        for (int i = 0; i < productsOfThisCategory.size(); i++) {
            if (i != 0)
                System.out.println("--------------------------");
            var product = productsOfThisCategory.get(i);
            System.out.println((i + 1) + ". " + product.getName() + " |  " + product.getPrice() + "$" + "  |  " + product.getProductId());
        }
    }

}
