package view.Profile.SellerMenu;

import control.Controller;
import control.Exceptions.InvalidAuctionIdException;
import control.Exceptions.InvalidProductIdException;
import control.SellerController;
import control.TokenGenerator;
import model.Auction;
import model.People.Seller;
import model.Product;
import model.Requests.AddAuctionRequest;
import model.Requests.EditAuctionRequest;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ViewOffsMenu extends Menu {

    private SellerController sellerController;

    public ViewOffsMenu(Menu parentMenu, SellerController sellerController) {
        super("View Offs", parentMenu);
        this.sellerController = sellerController;
        subMenus.put(1, new Menu("Add Off", this) {
            @Override
            public void executeMenu() {
                addOff();
            }
        });
        subMenus.put(2, new Menu("Edit Off", this) {
            @Override
            public void executeMenu() {
                editOffAttribute();
            }
        });
        subMenus.put(3, new Menu("View Off", this) {
            @Override
            public void executeMenu() {
                viewOffById();
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
    }

    private void addOff() {
        var seller = (Seller) Controller.getCurrentAccount();
        Date beginDate = getBeginDate();
        Date endDate = getEndDate(beginDate);
        ArrayList<Product> appliedProducts = new ArrayList<>(getAppliedProducts());
        int offPercentage = getOffPercentage();
        new AddAuctionRequest(TokenGenerator.generateRequestId(),
                new Auction(seller, beginDate, endDate,
                        appliedProducts, offPercentage), seller);
    }

    private Date getBeginDate() {
        try {
            return new SimpleDateFormat("dd//MM/yyyy").parse(inputInFormat("Enter Begin Date in format (dd/mm/yyyy) : "
                    , "\\d\\d/\\d\\d/\\d\\d\\d\\d"));
        } catch (ParseException e) {
            System.out.println("invalid date");
            getBeginDate();
        }
        return null;
    }

    private Date getEndDate(Date begin) {
        try {
            var date = new SimpleDateFormat("dd//MM/yyyy").parse(inputInFormat("Enter Begin Date in format (dd/mm/yyyy) : "
                    , "\\d\\d/\\d\\d/\\d\\d\\d\\d"));
            if (date.compareTo(begin) > 0)
                return date;
            else
                getEndDate(begin);
        } catch (ParseException e) {
            System.out.println("invalid date");
            getEndDate(begin);
        }
        return null;
    }

    private int getOffPercentage() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (Pattern.matches("[1-9][0-9]*", input))
                if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 99)
                    return Integer.parseInt(input);
            System.out.println("Invalid Input : Please Enter A Valid Number From 1 To 99 ! ");
        }
    }

    private List<Product> getAppliedProducts() {
        String productId;
        List<Product> appliedProducts = new ArrayList<>();
        while (!(productId = scanner.nextLine()).equalsIgnoreCase("end")) {
            try {
                var product = Product.getProductById(productId);
                appliedProducts.add(product);
            } catch (InvalidProductIdException e) {
                System.out.println("Invalid PID !");
            }
        }

        return appliedProducts;
    }

    private void editOffAttribute() {
        var auction = getAuctionToEdit();
        command = getAttribute();
        if (command.matches("(?i)begin\\s+date")) {
            new EditAuctionRequest(TokenGenerator.generateRequestId(), auction, command, getBeginDateForEdit());
        } else if (command.matches("(?i)end\\s+date")) {
            new EditAuctionRequest(TokenGenerator.generateRequestId(), auction, command, getEndDateForEdit());
        } else if (command.matches("(?i)off\\s+percentage")) {
            new EditAuctionRequest(TokenGenerator.generateRequestId(), auction, command, Integer.toString(getOffPercentage()));
        } else if (command.matches("(?i)add\\s+product")) {
            new EditAuctionRequest(TokenGenerator.generateRequestId(), auction, command, getProductToAddForEdit(auction));
        } else if (command.matches("(?i)remove\\s+product")) {
            new EditAuctionRequest(TokenGenerator.generateRequestId(), auction, command, getProductToRemoveForEdit(auction));
        }
    }

    private String getAttribute() {
        return inputInFormatWithError("Enter Auction Id you want to edit  :",
                "(?i)(begin\\s+date|end\\s+date|off\\+percentage|add\\s+product|remove\\s+product)", "Invalid Format");
    }

    private Auction getAuctionToEdit() {

        command = inputInFormatWithError("Enter Auction Id you want to edit  :", "AUC_\\d{5}", "Invalid Format");
        var auction = Auction.getAuctionById(command);
        if (auction != null) return auction;
        else return getAuctionToEdit();

    }

    private String getBeginDateForEdit() {
        return inputInFormat("Enter Begin Date in format (dd/mm/yyyy) : ", "\\d\\d/\\d\\d/\\d\\d\\d\\d");
    }

    private String getEndDateForEdit() {
        return inputInFormat("Enter End Date in format (dd/mm/yyyy) : ", "\\d\\d/\\d\\d/\\d\\d\\d\\d");
    }

    private String getProductToAddForEdit(Auction auction) {
        Product product = null;
        String pid = "";
        while (product == null || auction.getAppliedProducts().contains(product)) {
            pid = scanner.nextLine();
            try {
                product = Product.getProductById(pid);
            } catch (InvalidProductIdException e) {
                System.out.println("invalid PID !");
            }
        }
        return pid;
    }

    private String getProductToRemoveForEdit(Auction auction) {
        Product product = null;
        String pid = "";
        while (product == null || !auction.getAppliedProducts().contains(product)) {
            pid = scanner.nextLine();
            try {
                product = Product.getProductById(pid);
            } catch (InvalidProductIdException e) {
                System.out.println("invalid PID !");
            }
        }
        return pid;
    }

    private void viewOffById() {
        String id = inputInFormat("Enter Off Id : ", "\\w+");
        if (command.equalsIgnoreCase("back")) return;
        try {
            System.out.println(sellerController.viewOffById(id).toString());
        } catch (InvalidAuctionIdException e) {
            System.out.println("Wrong Off Id ! ");
            viewOffById();
        }
    }
}
