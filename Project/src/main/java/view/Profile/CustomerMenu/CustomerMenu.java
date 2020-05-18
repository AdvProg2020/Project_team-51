package view.Profile.CustomerMenu;

import view.Menu;

import java.util.List;

public class CustomerMenu extends Menu {
    private CustomerMenu customerMenu;

    public CustomerMenu(Menu parentMenu, CustomerMenu customerMenu) {
        super("Customer Menu", parentMenu);
        this.customerMenu = customerMenu;
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
