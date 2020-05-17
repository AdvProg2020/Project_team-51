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
    }

    @Override
    public void showMenu() {
        System.out.println("- Offs");
        System.out.println("- Show Product [PID]");
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        command = inputInFormat("Please input a valid command : ", MenusPattern.AUCTION.getRegex());
        if (command.matches(AllCommands.OFFS.getRegex())) {
            var productsWithOffLists = new Menu("productLists", this) {

                @Override
                public void showMenu() {
                    System.out.println("- Show Product [PID]");
                    System.out.println("- Sorting");
                    System.out.println("- Filtering");
                }

                @Override
                public void executeMenu() {
                    menusHistory.push(this);
                    command = inputInFormat("Choose : " , MenusPattern.PRODUCTS.getRegex());
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
            };
            showProductsWithOffs();
            productsWithOffLists.showMenu();
            productsWithOffLists.executeMenu();
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
        Product product;
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
