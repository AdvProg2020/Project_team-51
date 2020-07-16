package view.Profile.ManagerMenu;

import control.Controller;
import control.ManagerController;
import model.OffCode;
import model.People.Account;
import model.Product;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageOffCodes extends Menu {
    ManagerController managerController;

    public ManageOffCodes(Menu parentMenu, ManagerController mc) {
        super("manage off codes", parentMenu);
        managerController = mc;
        subMenus.put(1, new Menu("show discount codes", this) {
            @Override
            public void executeMenu() {
                showDiscountCodes();
            }
        });
        subMenus.put(2, new Menu("show discount code", this) {
            @Override
            public void executeMenu() {
                viewSingleDiscountCode();
            }
        });
        subMenus.put(3, new Menu("edit discount code", this) {
            @Override
            public void executeMenu() {
                editDiscountCode();
            }
        });
        subMenus.put(4, new Menu("create discount code", this) {
            @Override
            public void executeMenu() {
                createDiscountCode();
            }
        }); //incomplete
        subMenus.put(5, new Menu("remove discount code", this) {
            @Override
            public void executeMenu() {
                removeDiscountCode();
            }
        });
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

    }

    private void showDiscountCodes() {
        for (OffCode o : managerController.getAllOffcodes()) {
            System.out.println(o.getOffCode() + " : off percent: " + o.getOffPercentage() + " from" + o.getBeginDate() + " to " + o.getEndDate());
        }
    }

    private void viewSingleDiscountCode() {
        try {
            String id = inputInFormat("enter code to view", "^\\w+$");
            OffCode o = OffCode.getOffIdById(id);
            System.out.println("code : " + o.getOffCode() +
                    "  begin date : " + o.getBeginDate() +
                    "  end date : " + o.getEndDate() +
                    "  off percent : " + o.getOffPercentage());
            System.out.println("applied accounts : ");
            for (Account account : o.getAppliedAccounts()) {
                System.out.println(account.getUserName());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            viewSingleDiscountCode();
        }
    }

    private void editDiscountCode() {
        String code = inputInFormat("enter off code", "\\w");
        var m = new Menu("edit discont code", this) {
            @Override
            public void showMenu() {
                System.out.println("1. change begin date");
                System.out.println("2. change end date");
                System.out.println("3. maximum off");
                System.out.println("4.change off percent");
                System.out.println("5. add account");
                System.out.println("6. remove account");
            }

            @Override
            public void executeMenu() {
                menusHistory.push(this);
                command = inputInFormat("select a field to change", "^[1-6]$");
                if (command.equals("1")) changeBeginDate(code);
                else if (command.equals("2")) changeEndDate(code);
                else if (command.equals("3")) changeMaxOff(code);
                else if (command.equals("4")) changeOffPercent(code);
                else if (command.equals("5")) addAccount(code);
                else if (command.equals("6")) removeAccount(code);

                else executeMenu();
            }
        };
        m.showMenu();
        m.executeMenu();
    }

    private void changeBeginDate(String code) {
        try {
            command = inputInFormat("enter new begin date in format dd/MM/yyyy", "^\\d{2}/\\d{2}/\\d{4}$");
            //managerController.editDiscountCode(code, null, command, null, 0, null);
            System.err.println("this function is not useful anymore");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void changeEndDate(String code) {
        try {
            inputInFormat("enter end date in formad dd/MM/yyyy : ", "\\d{2}/d{2}/d{4}");
            System.err.println("this function is not useful anymore");
            //managerController.editDiscountCode(code, null, null, command, 0, null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void changeMaxOff(String code) {
        try {
            command = inputInFormat("plesae enter new maximum off amount : ", "^\\+?\\d+\\.?\\d*$");
            System.err.println("this function is not useful anymore");
            //managerController.editDiscountCode(code, null, null, null, 0, Double.parseDouble(command));
        } catch (Exception e) {
            System.err.println("must enter a positive double");
        }
    }

    private void changeOffPercent(String code) {
        try {
            command = inputInFormat("please enter new off percent", "\\d+");
            //managerController.editDiscountCode(code, null, null, null, Integer.parseInt(command), null);
            System.err.println("this function is not useful anymore");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void addAccount(String code) {
        String id = inputInFormat("enter an ID", "^\\w+$");
        try {
            managerController.addAccountToOffcode(code, id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void removeAccount(String code) {
        String id = inputInFormat("enter an ID", "^\\w+$");
        try {
            managerController.removeAccountFromOffcode(code, id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void removeDiscountCode() {
        command = inputInFormat("enter code to remove", "^\\w+$");
        try {
            managerController.removeDiscountCode(command);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void createDiscountCode() {
        String name = inputInFormat("please enter desired name", "^\\w+$");
        ArrayList<Account> accounts = new ArrayList<>();
        System.out.println("enter applied users id , enter @ to end");
        String id;
        while (!((id = scanner.nextLine()).equalsIgnoreCase("@"))) {
            System.out.println(id);
            try {
                accounts.add(managerController.getAccountById(id));
            } catch (Exception e) {
                System.err.println("error: " + e.getMessage());
            }
        }
        System.out.println("enter applied products , enter @ to end");
        ArrayList<Product> products = new ArrayList<>();
        while (!(id = scanner.nextLine()).equalsIgnoreCase("@")) {
            try {
                products.add(managerController.getProductById(id));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        //System.out.println("enter start day in format dd/MM/YYYY");
        String startDateString;
        String endDateString;
        while (true) {
            try {
                startDateString = inputInFormat("enter start day in format dd/MM/YYYY", "^\\d{2}/\\d{2}/\\d{4}$");
                endDateString = inputInFormat("enter end day in format dd/MM/YYYY", "^\\d{2}/\\d{2}/\\d{4}$");
                checkDates(startDateString, endDateString);
                break;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }


        int offPercentage = Integer.parseInt(inputInFormat("enter off percentage", "^\\d{1,2}$"));
        double maxOff = Double.parseDouble(inputInFormat("enter maximum off amount", "^\\d+\\.?\\d*$"));
        try {
//            managerController.createDiscountCode(accounts, startDateString, endDateString, offPercentage, maxOff);
            System.out.println("code created successfully!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void checkDates(String startDateString, String endDateString) throws Exception {
        Date start = new SimpleDateFormat().parse(startDateString);
        Date end = new SimpleDateFormat().parse(endDateString);
        if (start.after(end)) throw new Exception("end date must be after start date");
    }


}