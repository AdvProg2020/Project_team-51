package view;

import control.Exceptions.NoCategoriesFoundException;
import control.ProductController;
import model.Category;
import model.Product;

import java.util.List;

public class ProductMenu extends Menu {

    private Category category;
    private List<Category> subCategories;
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
        System.out.println(size + 1 + ". Show Products");
        System.out.println(size + 2 + ". Back");
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
        int option = getOptionWithRange(1, size + 2);
        if (option <= size) {
            var nextMenu = subMenus.get(option - 1);
            nextMenu.showMenu();
            nextMenu.executeMenu();
        } else if (option == size + 1) {
            productsOfThisCategory = ProductController.showProductsOfThisCategory(category);
            listProducts();
            var productLists = new ProductList(this);
            productLists.showMenu();
            productLists.executeMenu();
        } else if (option == size + 2) {
            back();
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
