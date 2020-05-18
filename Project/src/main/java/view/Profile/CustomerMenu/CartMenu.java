package view.Profile.CustomerMenu;

import control.CustomerController;
import view.Menu;

public class CartMenu extends Menu {

    private CustomerController customerController;

    public CartMenu(Menu parentMenu, CustomerController customerController) {
        super("Cart Menu", parentMenu);
        this.customerController = customerController;
    }

    @Override
    public void executeMenu() {

    }

    private void viewCart() {

    }

    private void showProducts() {

    }

    private void viewProductById(String id) {

    }

    private void increaseProductQuantity(String id) {

    }

    private void decreaseProductQuantity(String id) {

    }

    private void showTotalPrice() {

    }

    private void purchase() {

    }
}
