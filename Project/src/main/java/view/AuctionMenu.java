package view;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.ProductController;
import model.Auction;
import model.People.Seller;
import model.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class AuctionMenu extends Menu{

    public AuctionMenu(Menu parentMenu) {
        super("Auction Menu", parentMenu);
        subMenus.put(1,new LoginMenu(this));
    }


    @Override
    public void showMenu() {
        System.out.println("1. Offs");
        System.out.println("2. Show Product [PID]");
        System.out.println("3. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("4. login");
        else {
            System.out.println("5. logout");
        }
    }

    @Override
    public void executeMenu() {
        command = inputInFormat("Please input a valid command : ", "(?i)(offs|show\\s+product\\s+(\\S+)|" +
                "back|login|logout)");
        if (command.matches("(?i)offs")){
            var auctionPage = new Menu("Offs Lists Menu" , this){

                @Override
                public void executeMenu() {
                    showProductsWithOffs();
                    command = inputInFormat("" ,"(?i)(sort\\s+by\\s+(w+)|view\\s+sorts|back|login|logout)");
                    if(command.matches("(!?)sort\\s+by\\s+(w+)")){
                        ProductController.applySort(command.split("\\s+")[2]);
                    } else if (command.matches("(!?)view\\s+sorts")){
                        var sort = new SortMenu(this) ;
                        sort.showAvailableSorts();
                    } else if (command.matches("(!?)back")) {
                        back();
                    } else if (command.matches("(?i)login") && Controller.getCurrentAccount()==null){
                        var login = new LoginMenu(this);
                        login.showMenu();
                        login.executeMenu();
                    } else if (command.matches("(?i)logout") && Controller.getCurrentAccount()!=null){
                        logout();
                    }
                }
            };
            auctionPage.executeMenu();
        } else if (command.matches("(?i)show\\s+product\\s+(\\S+)")) {
            showProduct(command.split("\\s")[2]);
        } else if (command.matches("(?i)back")){
            back();
        } else if (command.matches("(?i)login") && Controller.getCurrentAccount()==null){
            var login = subMenus.get(1);
            login.showMenu();
            login.executeMenu();
        } else if (command.matches("(?i)logout") && Controller.getCurrentAccount()!=null){
            logout();
        }
        this.executeMenu();
    }


    private void showProductsWithOffs() {
        var currentSort = ProductController.getCurrentSort().getSort();
        var productsOfThisCategory = currentSort.applySort(getProductsWithOffs() , currentSort.getAscending());
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
            return;
        }
        var productPage = new ProductPageMenu(this , product);
        productPage.showMenu();
        productPage.executeMenu();
    }

    private ArrayList<Product> getProductsWithOffs(){

        return new ArrayList<>(Seller.getAllSellers().stream().map(Seller::getAllAuctions).collect(Collectors.toList())
                .stream().flatMap(Collection::stream).map(Auction::getAppliedProducts).flatMap(Collection::stream)
                .collect(Collectors.toList()));


    }
}
