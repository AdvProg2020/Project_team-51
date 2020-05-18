package view.Profile.SellerMenu;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import control.Exceptions.WrongFormatException;
import control.SellerController;
import model.Product;
import view.Enums.AllCommands;
import view.Enums.MenusPattern;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

import javax.management.InstanceAlreadyExistsException;

public class SellerMenu extends Menu {

    private SellerController sellerController;

    public SellerMenu(Menu parentMenu) {
        super("Seller Menu", parentMenu);
        this.sellerController = new SellerController(Controller.getCurrentAccount());
        subMenus.put(1, new Menu("Personal Info", this) {
            @Override
            public void executeMenu() {
                viewPersonalInfo();
            }
        });
        subMenus.put(2, new Menu("Company Info", this) {
            @Override
            public void executeMenu() {
                viewCompanyInformation();
            }
        });
        subMenus.put(3, new Menu("Sales History", this) {
            @Override
            public void executeMenu() {
                viewSalesHistory();
            }
        });
        subMenus.put(4, new ManageProductsMenu(this, sellerController));
        subMenus.put(5, new Menu("Show Categories", this) {
            @Override
            public void executeMenu() {
                showCategories();
            }
        });
        subMenus.put(6, new ViewOffsMenu(this, sellerController));
        subMenus.put(7, new Menu("Balance", this) {
            @Override
            public void executeMenu() {
                viewSellerBalance();
            }
        });
        subMenus.put(8, new AddProductMenu(this, sellerController));
        subMenus.put(9, new Menu("Remove Product", this) {
            @Override
            public void executeMenu() {
                removeProduct();
            }
        });
    }

    @Override
    public void showMenu() {
        System.out.println("1. Personal Info");
        System.out.println("2. Company Info");
        System.out.println("3. Sales History");
        System.out.println("4. Manage Products");
        System.out.println("5. Show Categories");
        System.out.println("6. View Offs");
        System.out.println("7. Balance");
        System.out.println("8. Add Product");
        System.out.println("9. Remove Product");
        System.out.println("10. Back");
        if (Controller.getCurrentAccount() == null)
            System.out.println("11. Login");
        else
            System.out.println("11. Logout");
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
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

        this.executeMenu();
    }

    private void viewPersonalInfo() {
        System.out.println(sellerController.viewPersonalInfo());
        var subMenu = new Menu("Edit Personal Info", this) {

            @Override
            public void showMenu() {
                System.out.println("- Edit");
            }

            @Override
            public void executeMenu() {
                menusHistory.push(this);
                command = inputInFormat("Select option : ", MenusPattern.SELLER_PERSONAL_INFO.getRegex());
                if (command.matches(AllCommands.EDIT_PERSONAL_INFO.getRegex())) {
                    editPersonalInfoField(this);
                } else if (command.matches(AllCommands.BACK.getRegex())) {
                    back();
                } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
                    logout();
                }

                this.executeMenu();
            }
        };
    }

    private void viewCompanyInformation() {
        System.out.println(sellerController.viewCompanyInfo());
        command = inputInFormat("write Back in order to get back to last Menu : ", AllCommands.BACK.getRegex());
    }

    private void viewSalesHistory() {
        sellerController.viewSalesHistory().forEach(System.out::println);
        command = inputInFormat("write Back in order to get back to last Menu : ", AllCommands.BACK.getRegex());
    }

    private void removeProduct() {
        String id = inputInFormat("Enter a Product id : ", "\\w+");
        Product product = null;
        try {
            product = Product.getProductById(id);
            sellerController.removeProduct(product);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }
    }

    private void showCategories() {
        sellerController.showCategories().forEach(System.out::println);
    }

    private void viewSellerBalance() {
        System.out.println(sellerController.getBalance() + " $");
    }

    private void editPersonalInfoField(Menu parent) {
        var editPersonalInfo = new Menu("Edit Personal Info ", parent) {

            @Override
            public void showMenu() {
                System.out.println("First Name : " + "\n" +
                        "Last Name : " + "\n" +
                        "Email : " + "\n" +
                        "Phone : " + "\n" +
                        "Brand ");
            }

            @Override
            public void executeMenu() {
                menusHistory.push(this);
                command = SellerMenu.this.inputInFormat("Select Option : ",
                        MenusPattern.EDIT_SELLER_PERSONAL_INFO.getRegex());
                if (command.matches(AllCommands.FIRST_NAME.getRegex())) {
                    getFirstName();
                } else if (command.matches(AllCommands.LAST_NAME.getRegex())) {
                    getLastName();
                } else if (command.matches(AllCommands.EMAIL.getRegex())) {
                    getEmail();
                } else if (command.matches(AllCommands.PHONE.getRegex())) {
                    getPhone();
                } else if (command.matches(AllCommands.BRAND.getRegex())) {
                    getBrand();
                } else if (command.matches(AllCommands.BACK.getRegex())) {
                    back();
                } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
                    logout();
                }
            }
        };
        editPersonalInfo.showMenu();
        editPersonalInfo.executeMenu();
    }

    private void getFirstName() {
        System.out.println("Please Enter Your New First Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        try {
            SellerController.editFirstName(command);
            System.out.println("New first name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old first name ! ");
            getFirstName();
        }
    }

    private void getLastName() {
        System.out.println("Please Enter Your New Last Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        try {
            SellerController.editLastName(command);
            System.out.println("New last name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old last name ! ");
            getLastName();
        }
    }

    private void getEmail() {
        System.out.println("Please Enter Your New Email : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+@\\w+\\.\\w+");
        try {
            SellerController.editEmail(command);
            System.out.println("New email submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old email ! ");
            getEmail();
        } catch (IllegalArgumentException e) {
            System.out.println("Email has already used for other Account");
            getEmail();
        }
    }

    private void getPhone() {
        System.out.println("Please Enter Your New Phone Number : ");
        command = inputInFormat("Invalid Format !", "(?i)[0-9]+");
        try {
            SellerController.editPhoneNumber(command);
            System.out.println("New phoneNumber submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old phone number ! ");
            getPhone();
        } catch (WrongFormatException e) {
            System.out.println("Phone number has already used for other Account");
            getPhone();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Format !");
            getPhone();
        }
    }

    private void getBrand() {
        System.out.println("Please Enter Your New Brand Name : ");
        command = inputInFormat("Invalid Format !", "(?i)\\w+");
        try {
            SellerController.editBrand(command);
            System.out.println("New brand name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old brand name ! ");
            getBrand();
        }
    }

}

