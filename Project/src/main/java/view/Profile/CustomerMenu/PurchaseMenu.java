package view.Profile.CustomerMenu;

import control.CustomerController;
import view.Menu;

public class PurchaseMenu extends Menu {

    private CustomerController customerController;

    public PurchaseMenu(Menu parentMenu, CustomerController customerController) {
        super("Purchase", parentMenu);
        this.customerController = customerController;
    }

    @Override
    public void executeMenu() {

    }
}
