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
}
