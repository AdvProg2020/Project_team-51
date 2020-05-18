package view.Profile.CustomerMenu;

import control.Controller;
import control.CustomerController;
import view.Menu;

import java.util.List;

public class CustomerMenu extends Menu {

    private CustomerController customerController;

    public CustomerMenu(Menu parentMenu) {
        super("Customer Menu", parentMenu);
        customerController = new CustomerController(Controller.getCurrentAccount());
    }

    @Override
    public void executeMenu() {

    }

    public void customerMenu() {

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

    private List<String> getReceiverInformation() {
        return null;
    }

    private String getBuyerDiscountCode() {
        return null;
    }

    private void viewOrders() {

    }

    private void showOrderById(String id) {

    }

    private void rateProduct(String id, int score) {

    }

    private void viewBalance() {

    }

    private void viewDiscountCodes() {

    }
}
