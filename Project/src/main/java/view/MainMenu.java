package view;

import control.Controller;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import view.Profile.CustomerMenu;
import view.Profile.ManagerMenu;
import view.Profile.SellerMenu.SellerMenu;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Main Menu", null);
        var person = Controller.getCurrentAccount();
        if (person == null)
            subMenus.put(1, new UnknownMenu("Profile Area", this));
        else if (person instanceof Customer)
            subMenus.put(1, new CustomerMenu(this));
        else if (person instanceof Seller)
            subMenus.put(1, new SellerMenu(this));
        else if (person instanceof Manager)
            subMenus.put(1, new ManagerMenu(this));

        subMenus.put(2, new ProductMenu(this, null));
        subMenus.put(3, new AuctionMenu(this));
        subMenus.put(4, new LoginMenu(this));

        this.showMenu();
        this.executeMenu();
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        Menu nextMenu = null;
        int size = subMenus.size();
        int option = getOptionWithRange(1, size);

        if (option == (size + 1))
            System.exit(0);
        else if (option <= size) {
            nextMenu = subMenus.get(option);
        }

        nextMenu.showMenu();
        nextMenu.executeMenu();
    }


}
