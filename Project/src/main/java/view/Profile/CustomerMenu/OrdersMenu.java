package view.Profile.CustomerMenu;

import control.Controller;
import control.CustomerController;
import control.Exceptions.InvalidProductIdException;
import model.OrderLog.Order;
import model.Product;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

import java.util.List;

public class OrdersMenu extends Menu {


    private CustomerController customerController;
    private List<Order> orders;

    public OrdersMenu(Menu parentMenu, CustomerController customerController) {
        super("Orders History Menu", parentMenu);
        this.customerController = customerController;
        this.orders = customerController.getOrders();
        subMenus.put(1, new Menu("Show Order", this) {
            @Override
            public void executeMenu() {
                showOrderById();
            }
        });
        subMenus.put(2, new Menu("Rate Product", this) {
            @Override
            public void executeMenu() {
                rateProduct();
            }
        });
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        viewOrders();
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
                var login = new LoginMenu(this);
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

    private void viewOrders() {
        if (orders == null || orders.isEmpty()) {
            System.out.println("You have no Order !");
            return;
        }
        orders.forEach(a -> System.out.println(a.toString()));
    }

    private void showOrderById() {
        String id = inputInFormat("Please enter a valid order Id :", "\\w+");
        if (id.equalsIgnoreCase("back")) return;
        var order = Order.getOrderById(id);
        if (order == null) {
            System.out.println("Invalid order Id !");
            showOrderById();
        } else {
            System.out.println(order);
        }
    }

    private void rateProduct() {
        String productId = inputInFormat("Enter A valid Product Id :", "\\w+");
        if (productId.equalsIgnoreCase("back")) return;
        try {
            var product = Product.getProductById(productId);
            System.out.println("Enter Your Rate (0-5) :");
            int rate = getOptionWithRange(0, 3);
            try {
                customerController.rateProduct(product, rate);
                System.out.println("Rate submitted successfully !");
            } catch (InvalidProductIdException e) {
                System.out.println("You can't rate this product !");
                rateProduct();
            }
        } catch (InvalidProductIdException e) {
            System.out.println("Invalid Product Id !");
            rateProduct();
        }
    }
}
