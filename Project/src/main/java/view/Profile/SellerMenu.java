package view.Profile;

import control.Controller;
import control.Exceptions.InvalidAuctionIdException;
import control.Exceptions.InvalidProductIdException;
import control.Exceptions.WrongFormatException;
import control.SellerController;
import control.TokenGenerator;
import model.Auction;
import model.People.Seller;
import model.Product;
import model.Requests.AddAuctionRequest;
import view.AllPatterns;
import view.Menu;
import view.MenusPattern;

import javax.management.InstanceAlreadyExistsException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class SellerMenu extends Menu {

    private SellerController sellerController ;

    public SellerMenu(Menu parentMenu) {
        super("Seller Menu", parentMenu);
        this.sellerController = new SellerController(Controller.getCurrentAccount());
    }

    @Override
    public void showMenu() {
        System.out.println("- Personal Info");
        System.out.println("- Company Info");
        System.out.println("- Sales History");
        System.out.println("- Manage Products");
        System.out.println("- Show Categories");
        System.out.println("- View Offs");
        System.out.println("- Balance");
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        command = inputInFormat("Select option : " , MenusPattern.SELLER.getRegex());
        if (command.matches(AllPatterns.PERSONAL_INFO.getRegex())){
            viewPersonalInfo(); //done
        } else if (command.matches(AllPatterns.COMPANY_INFO.getRegex())){
            viewCompanyInformation(); //done
        } else if (command.matches(AllPatterns.SALES_HISTORY.getRegex())){
            viewSalesHistory(); //done
        } else if (command.matches(AllPatterns.MANAGE_PRODUCTS.getRegex())){
            manageProducts();
        } else if (command.matches(AllPatterns.SHOW_CATEGORIES.getRegex())){
            showCategories(); //done
        } else if (command.matches(AllPatterns.VIEW_OFFS.getRegex())){
            viewOffs(this); //done
        } else if (command.matches(AllPatterns.BALANCE.getRegex())){
            viewSellerBalance(); //done
        } else if (command.matches(AllPatterns.BACK.getRegex())){
            back();
        } else if (command.matches(AllPatterns.LOGOUT.getRegex())){
            logout();
        }

        this.executeMenu();
    }

    private void viewPersonalInfo(){
        System.out.println(sellerController.viewPersonalInfo());
        var subMenu = new Menu("Edit Personal Info" , this) {

            @Override
            public void showMenu() {
                System.out.println("- Edit");
            }

            @Override
            public void executeMenu() {
                menusHistory.push(this);
                command=inputInFormat("Select option : ",MenusPattern.SELLER_PERSONAL_INFO.getRegex());
                if (command.matches(AllPatterns.EDIT_PERSONAL_INFO.getRegex())){
                    editPersonalInfoField(this);
                } else if (command.matches(AllPatterns.BACK.getRegex())){
                    back();
                } else if (command.matches(AllPatterns.LOGOUT.getRegex())){
                    logout();
                }

                this.executeMenu();
            }
        };
    }

    private void viewCompanyInformation() {
        System.out.println(sellerController.viewCompanyInfo());
        command = inputInFormat("write Back in order to get back to last Menu : " , AllPatterns.BACK.getRegex());
    }

    private void viewSalesHistory() {
        sellerController.viewSalesHistory().stream().forEach(System.out::println);
        command = inputInFormat("write Back in order to get back to last Menu : " , AllPatterns.BACK.getRegex());
    }

    private void manageProducts() {

    }

    private void viewProductBuyers(String id) {

    }

    private void editProduct(String id) {

    }

    private void addProduct() {

    }

    private void deleteProduct(String id) {

    }

    private void showCategories(){
        sellerController.showCategories().stream().forEach(System.out::println);
    }

    private void viewOffs(Menu parent){
        sellerController.viewOffs().stream().forEach(System.out::println);
        var offsMenu = new Menu("Offs Menu in Seller Account : " , parent){

            @Override
            public void showMenu() {
            }

            @Override
            public void executeMenu() {
                menusHistory.push(this);
                command = inputInFormat("you can view,edit or add an off : " ,"(?i)(add\\s+off|" +
                                                                                         "edit\\s+(\\w+)|view\\s+(\\w+))");
                if (command.matches("(?i)add\\s+off")){
                    addOff();
                } else if (command.matches("(?i)edit")){
                    editOffAttribute(this );
                } else if (command.matches("(?i)view\\s+(\\w+)")){
                    viewOffById(command.split("\\s+")[1]);
                } else if (command.matches(AllPatterns.BACK.getRegex())){
                    back();
                } else if (command.matches(AllPatterns.LOGOUT.getRegex())){
                    logout();
                }

                this.executeMenu();
            }
        };
    }

    private void viewOffById(String id){
        try {
            System.out.println(sellerController.viewOffById(id).toString());
        } catch (InvalidAuctionIdException e) {
            System.out.println("Wrong Off Id ! ");
        }
    }

    private void editOffAttribute(Menu parent){

    }

    // Seller seller ,Date beginDate, Date endDate, ArrayList<Product> appliedProducts, int offPercentage
    private void addOff(){
        var seller = (Seller)Controller.getCurrentAccount();
        Date beginDate = getBeginDate();
        Date endDate = getEndDate(beginDate);
        ArrayList<Product> appliedProducts = new ArrayList<>(getAppliedProducts());
        int offPercentage = getOffPercentage();
        new AddAuctionRequest(TokenGenerator.generateRequestId(),
                              new Auction(seller,beginDate,endDate,
                              appliedProducts,offPercentage),seller);
    }

    private int getOffPercentage(){
        String input;
        while (true) {
            input = scanner.nextLine();
            if (Pattern.matches("[1-9][0-9]*", input))
                if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= 99)
                    return Integer.parseInt(input);
            System.out.println("Invalid Input : Please Enter A Valid Number From 1 To 99 ! ");
        }
    }

    private ArrayList<Product> getAppliedProducts(){
        String productId = "" ;
        ArrayList<Product> appliedProducts = new ArrayList<>();
        while (!(productId=scanner.nextLine()).equalsIgnoreCase("end")){
            try {
                var product = Product.getProductById(productId);
                appliedProducts.add(product);
            } catch (InvalidProductIdException e) {
                System.out.println("Invalid PID !");
            }
        }

        return appliedProducts;
    }

    private Date getBeginDate(){
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
            } catch(ParseException e){
                System.out.println("invalid date");
                getEndDate(begin);
            }
            return null;
        }

    private void viewSellerBalance(){
        System.out.println(sellerController.getBalance() + " $");
     }

    private void editPersonalInfoField(Menu parent){
        var editPersonalInfo = new Menu("Edit Personal Info " , parent){

            @Override
            public void showMenu() {
                System.out.println("First Name : "+ "\n" +
                        "Last Name : "  + "\n" +
                        "Email : " + "\n" +
                        "Phone : " + "\n" +
                        "Brand ");
            }

            @Override
            public void executeMenu() {
                menusHistory.push(this);
                command = SellerMenu.this.inputInFormat("Select Option : ",
                                                        MenusPattern.EDIT_SELLER_PERSONAL_INFO.getRegex());
                if (command.matches(AllPatterns.FIRST_NAME.getRegex())){
                    getFirstName();
                } else if (command.matches(AllPatterns.LAST_NAME.getRegex())){
                    getLastName();
                } else if (command.matches(AllPatterns.EMAIL.getRegex())){
                    getEmail();
                } else if (command.matches(AllPatterns.PHONE.getRegex())){
                    getPhone();
                } else if (command.matches(AllPatterns.BRAND.getRegex())){
                    getBrand();
                } else if (command.matches(AllPatterns.BACK.getRegex())){
                    back();
                } else if (command.matches(AllPatterns.LOGOUT.getRegex())){
                    logout();
                }
            }
        };
        editPersonalInfo.showMenu();
        editPersonalInfo.executeMenu();
    }

    private void getFirstName(){
        System.out.println("Please Enter Your New First Name : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+");
        try {
            sellerController.editFirstName(command);
            System.out.println("New first name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old first name ! ");
            getFirstName();
        }
    }

    private void getLastName(){
        System.out.println("Please Enter Your New Last Name : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+");
        try {
            sellerController.editLastName(command);
            System.out.println("New last name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old last name ! ");
            getLastName();
        }
    }

    private void getEmail(){
        System.out.println("Please Enter Your New Email : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+@\\w+\\.\\w+");
        try {
            sellerController.editEmail(command);
            System.out.println("New email submitted !");
        } catch (InstanceAlreadyExistsException e ) {
            System.out.println("This is your old email ! ");
            getEmail();
        } catch (IllegalArgumentException e){
            System.out.println("Email has already used for other Account");
            getEmail();
        }
    }

    private void getPhone(){
        System.out.println("Please Enter Your New Phone Number : ");
        command=inputInFormat("Invalid Format !" , "(?i)[0-9]+");
        try {
            sellerController.editPhoneNumber(command);
            System.out.println("New phoneNumber submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old phone number ! ");
            getPhone();
        } catch (WrongFormatException e) {
            System.out.println("Phone number has already used for other Account");
            getPhone();
        } catch (IllegalArgumentException e){
            System.out.println("Invalid Format !");
            getPhone();
        }
    }

    private void getBrand(){
        System.out.println("Please Enter Your New Brand Name : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+");
        try {
            sellerController.editBrand(command);
            System.out.println("New brand name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old brand name ! ");
            getBrand();
        }
    }





}
