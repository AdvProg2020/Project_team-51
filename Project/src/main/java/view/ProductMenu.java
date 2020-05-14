package view;

import control.Exceptions.InvalidProductIdException;
import control.ProductController;
import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductMenu extends Menu {

    private Category category;
    private ArrayList<Product> productsOfThisCategory = null ;

    public ProductMenu(Menu parentMenu , Category category) {
        super("Products Menu", parentMenu);
        int size = 0;
        if (category!=null){
            size = category.getSubCategories().size();
            for (Map.Entry<Integer, Category> categoryEntry : category.getSubCategories().entrySet()) {
                subMenus.put(categoryEntry.getKey() , new ProductMenu(this , categoryEntry.getValue()));
            }
        }
    }

    @Override
    public void showMenu() {
        int size = 0;
        if (category!=null) {
            size = category.getSubCategories().size();
            viewCategories();
        }
        System.out.println("----------------------------");
        System.out.println(size+1 + ". Show Products");
        System.out.println(size+2 + ". Back");
    }

    @Override
    public void executeMenu() {
        int size =0 ;
        if(category!=null)
        size = category.getSubCategories().size();
        while (true) {
            int option = getOption();
            if (option <= size){
                var nextMenu = subMenus.get(option);
                nextMenu.showMenu();
                nextMenu.executeMenu();
                break;
            }
            else if (option==size+1){
                productsOfThisCategory = ProductController.showProductsOfThisCategory(category);
                listProducts();
                var productLists = new Menu("productLists", this){

                    @Override
                    public void showMenu() {
                        System.out.println("1. Show Product [PID]");
                        System.out.println("2. Sorting");
                        System.out.println("3. Filtering");
                        System.out.println("4. Back");
                    }

                    @Override
                    public void executeMenu() {

                            command = inputInFormat("Choose : " , "(?i)(show\\s+product\\s+([0-9]+) | " +
                                    "sorting|filtering|back)").trim();
                            if (command.matches("(?i)show\\s+product\\s+([0-9]+)")){
                                showProduct(command.split("\\s")[2]);
                            } else if (command.matches("(?i)sorting")){
                                var sort = new SortMenu(this) ;
                                sort.showMenu();
                                sort.executeMenu();
                            } else if (command.matches("(?i)filtering")){
                                var filter = new FilterMenu(this) ;
                                filter.showMenu();
                                filter.executeMenu();
                            } else if (command.matches("(?i)back")){
                                back();
                            }

                        this.showMenu();
                        this.executeMenu();
                    }
                };
                break;
            }
            else if (option==size+2){
                back();
                break;
            }
        }
    }

    private void viewCategories() {
        for (Map.Entry<Integer, Category> category : category.getSubCategories().entrySet()) {
            System.out.println(category.getKey() + ". " + category.getValue().getName());
        }
    }



    private void listProducts(){
        var currentSort = ProductController.getCurrentSort();
        var productsOfThisCategory = currentSort.applySort(this.productsOfThisCategory , currentSort.getAscending());
        for (int i = 0; i < productsOfThisCategory.size(); i++) {
            if (i!=0)
            System.out.println("--------------------------");
            var product = productsOfThisCategory.get(i);
            System.out.println((i+1) + ". " + product.getName() + " |  " + product.getPrice() + "$" + "  |  " + product.getProductId());
        }
    }

    private void showProduct(String pid) {
        Product product = null;
        try {
            product = Product.getProductById(pid);
        } catch (InvalidProductIdException e) {
            System.out.println(e.getMessage());
        }
        var productPage = new ProductPageMenu(this , product);
        productPage.showMenu();
        productPage.executeMenu();
    }

}
