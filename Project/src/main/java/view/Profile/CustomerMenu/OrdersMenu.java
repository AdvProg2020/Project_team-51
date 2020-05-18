package view.Profile.CustomerMenu;

import control.CustomerController;
import view.Menu;

public class OrdersMenu extends Menu {


    private CustomerController customerController;

    public OrdersMenu(Menu parentMenu, CustomerController customerController) {
        super("Orders History Menu", parentMenu);
        this.customerController = customerController;
    }

    @Override
    public void executeMenu() {

    }
}
