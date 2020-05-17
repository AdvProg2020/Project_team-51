package view.Profile;

import control.Controller;
import control.Exceptions.InvalidAuctionIdException;
import control.Exceptions.InvalidProductIdException;
import control.Exceptions.NotAllowedActivityException;
import control.Exceptions.WrongFormatException;
import control.SellerController;
import control.TokenGenerator;
import model.Attributes;
import model.Auction;
import model.Category;
import model.People.Seller;
import model.Product;
import model.Requests.AddAuctionRequest;
import model.Requests.AddSellerForItemRequest;
import model.Requests.EditAuctionRequest;
import view.AllCommands;
import view.Menu;
import view.MenusPattern;

import javax.management.InstanceAlreadyExistsException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        System.out.println("- Add Product");
        System.out.println("- Remove Product");
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        command = inputInFormat("Select option : " , MenusPattern.SELLER.getRegex());
        if (command.matches(AllCommands.PERSONAL_INFO.getRegex())) {
            viewPersonalInfo(); //done
        } else if (command.matches(AllCommands.COMPANY_INFO.getRegex())) {
            viewCompanyInformation(); //done
        } else if (command.matches(AllCommands.SALES_HISTORY.getRegex())) {
            viewSalesHistory(); //done
        } else if (command.matches(AllCommands.MANAGE_PRODUCTS.getRegex())) {
            manageProducts();
        } else if (command.matches(AllCommands.SHOW_CATEGORIES.getRegex())) {
            showCategories(); //done
        } else if (command.matches(AllCommands.VIEW_OFFS.getRegex())) {
            viewOffs(this); //done
        } else if (command.matches(AllCommands.BALANCE.getRegex())) {
            viewSellerBalance(); //done
        } else if (command.matches(AllCommands.ADD_PRODUCT.getRegex())) {
            addProduct(this);
        } else if (command.matches(AllCommands.REMOVE_PRODUCT.getRegex())) {
            removeProduct(command.split("\\s+")[2]);
        } else if (command.matches(AllCommands.BACK.getRegex())) {
            back();
        } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
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

    private void manageProducts() {
        sellerController.showSellersProducts().forEach(System.out::println);
        command = inputInFormat("You Can View a Product Info,Buyers or Edit it: ",
                                MenusPattern.MANAGE_PRODUCTS.getRegex());
        if (command.matches(AllCommands.VIEW_PID.getRegex())) {
            viwProductDetails(command.split("\\s+")[1]); //done
        } else if (command.matches(AllCommands.VIEW_BUYERS_PID.getRegex())) {
            viewProductBuyers(command.split("\\s+")[2]); //done
        } else if (command.matches(AllCommands.EDIT_PID.getRegex())) {
            editProduct(command.split("\\s+")[1]);
        } else if (command.matches(AllCommands.BACK.getRegex())) {
            back();
        } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
            logout();
        }

        manageProducts();

    }

    private void viewProductBuyers(String id) {

        Product product ;
        try {
            product = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }

        sellerController.viewProductBuyers(product).forEach(System.out::println);
    }

    private void viwProductDetails(String id){

        Product product ;
        try {
            product = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }

        System.out.println(sellerController.showProductDetails(product));
    }

    private void editProduct(String id) {
        Product product ;
        try {
            product = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }
        System.out.println("Name\nPrice\nDescription\nQuantity");
        String field = inputInFormat("select a option : " , "(?i)(name|price|description|quantity)");
        String value = inputInFormat("Enter a value for selected field : " , "\\W+");
        SellerController.editProduct(product,field,value);

    }

    private void addProduct(Menu parent) {
        var addProductMenu = new Menu("Add Product Menu : " , parent){

            @Override
            public void showMenu() {
                System.out.println("-New Product\n-Existed Product");
            }

            @Override
            public void executeMenu() {
                command = inputInFormat("Select a option : " , "(?i)(existed\\s+product|new\\s+product" +
                                                                                "|back|logout)");
                if (command.matches("existed\\s+product")) {
                    addExistedProduct();
                } else if (command.matches("new\\s+product")) {
                    addNewProduct();
                } else if (command.matches(AllCommands.BACK.getRegex())) {
                    back();
                } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
                    logout();
                }

                this.executeMenu();
            }
        };
        addProductMenu.showMenu();
        addProductMenu.executeMenu();
    }

    private void addExistedProduct(){
        Product product ;
        String id = inputInFormat("Please enter the product Id : " , "\\w+");
        try {
            product = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }
        System.out.println("Enter Quantity : ");
        int quantity = getOptionWithRange(0 , Integer.MAX_VALUE);
        System.out.println("Enter Price : ");
        double price = getOptionWithRangeDouble(0.00 , Double.MAX_VALUE);

        new AddSellerForItemRequest(TokenGenerator.generateRequestId(),product,
                                    (Seller)Controller.getCurrentAccount(), quantity,price);
    }

    private void showCategories(List<Category> categories){
        for (int i = 0; i < categories.size(); i++) {
            var category = categories.get(i);
            System.out.println((i+1)+ ". " + Category.getPathOfCategory(category) );
        }
    }

    private void addNewProduct(){
        var categories = sellerController.listCategories();
        String name = inputInFormat("Enter the name of product : " , "\\w+");
        String brand = inputInFormat("Enter the name of product : " , "\\w+");
        showCategories(categories);
        System.out.println("Enter product parent category number : ");
        int categoryOption = getOptionWithRange(1,categories.size());
        var parentCategory = categories.get(categoryOption-1);
        Map<Attributes,String> attribute = getAttributesToAddProduct(parentCategory);
        System.out.println("Enter product quantity : ");
        int quantity = getOptionWithRange(1,Integer.MAX_VALUE);
        System.out.println("Enter product price : ");
        double price = getOptionWithRangeDouble(0.00 , Double.MAX_VALUE);
        String description = inputInFormat("Enter a description for product : " , "\\w+");
        try {
            sellerController.addProduct(new Product(TokenGenerator.generateProductId(),name,brand,price,
                                                   (Seller)Controller.getCurrentAccount(),quantity
                                                    ,parentCategory,description,attribute));
            System.out.println("Adding Product Request Sent ! ");
        } catch (NotAllowedActivityException e) {
            System.out.println(e.getMessage());
        }
    }

    private Map<Attributes,String> getAttributesToAddProduct(Category parentCategory){
        return parentCategory.getAttributes().stream()
                .peek(System.out::println)
                .collect(Collectors.toMap(Function.identity(),a-> inputInFormat(
                                                                    "Please enter a value for field : ",
                                                                    "\\w+")));
    }

    private void removeProduct(String id) {
        Product product ;
        try {
            product = Product.getProductById(id);
        } catch (InvalidProductIdException e) {
            System.out.println("invalid product id");
            return;
        }
        sellerController.removeProduct(product);
    }

    private void showCategories(){
        sellerController.showCategories().forEach(System.out::println);
    }

    private void viewOffs(Menu parent){
        sellerController.viewOffs().forEach(System.out::println);
        var offsMenu = new Menu("Offs Manager Menu" , parent){

            @Override
            public void executeMenu() {
                menusHistory.push(this);
                command = inputInFormat("you can view,edit or add an off : " ,"(?i)(add\\s+off|" +
                                                                                         "edit\\s+(\\w+)|view\\s+(\\w+))");
                if (command.matches("(?i)add\\s+off")){
                    addOff(); // done
                } else if (command.matches("(?i)edit")) {
                    editOffAttribute(); //done
                } else if (command.matches("(?i)view\\s+(\\w+)")) {
                    viewOffById(command.split("\\s+")[1]); //done
                } else if (command.matches(AllCommands.BACK.getRegex())) {
                    back();
                } else if (command.matches(AllCommands.LOGOUT.getRegex())) {
                    logout();
                }

                this.executeMenu();
            }
        };
        offsMenu.showMenu();
        offsMenu.executeMenu();
    }

    private void viewOffById(String id){
        try {
            System.out.println(sellerController.viewOffById(id).toString());
        } catch (InvalidAuctionIdException e) {
            System.out.println("Wrong Off Id ! ");
        }
    }

    private void editOffAttribute(){
        var auction = getAuctionToEdit();
        command = getAttribute();
        if (command.matches("(?i)begin\\s+date")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,command,getBeginDateForEdit());
        } else if (command.matches("(?i)end\\s+date")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,command,getEndDateForEdit());
        } else if (command.matches("(?i)off\\s+percentage")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,command,Integer.toString(getOffPercentage()));
        } else if (command.matches("(?i)add\\s+product")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,command,getProductToAddForEdit(auction));
        } else if (command.matches("(?i)remove\\s+product")){
            new EditAuctionRequest(TokenGenerator.generateRequestId(),auction,command,getProductToRemoveForEdit(auction));
        }
    }

    private String getProductToAddForEdit(Auction auction){
        Product product = null;
        String pid = "";
        while ( product==null || auction.getAppliedProducts().contains(product)) {
            pid = scanner.nextLine();
            try {
                product= Product.getProductById(pid);
            } catch (InvalidProductIdException e) {
                System.out.println("invalid PID !");
            }
        }
        return pid ;
    }

    private String getProductToRemoveForEdit(Auction auction){
        Product product = null;
        String pid = "";
        while ( product==null || !auction.getAppliedProducts().contains(product)) {
            pid = scanner.nextLine();
            try {
                product= Product.getProductById(pid);
            } catch (InvalidProductIdException e) {
                System.out.println("invalid PID !");
            }
        }
        return pid;
    }

    private String getAttribute(){
        return inputInFormatWithError("Enter Auction Id you want to edit  :" ,
                "(?i)(begin\\s+date|end\\s+date|off\\+percentage|add\\s+product|remove\\s+product)", "Invalid Format");
    }

    private Auction getAuctionToEdit(){

        command = inputInFormatWithError("Enter Auction Id you want to edit  :" , "AUC_\\d{5}"  , "Invalid Format");
        var auction = Auction.getAuctionById(command);
        if (auction!=null) return auction;
        else return  getAuctionToEdit();

    }

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
        String productId;
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

    private String getBeginDateForEdit(){
        return inputInFormat("Enter Begin Date in format (dd/mm/yyyy) : ", "\\d\\d/\\d\\d/\\d\\d\\d\\d");
    }

    private String getEndDateForEdit(){
        return inputInFormat("Enter End Date in format (dd/mm/yyyy) : ", "\\d\\d/\\d\\d/\\d\\d\\d\\d");
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

    private void getFirstName(){
        System.out.println("Please Enter Your New First Name : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+");
        try {
            SellerController.editFirstName(command);
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
            SellerController.editLastName(command);
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
            SellerController.editEmail(command);
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
            SellerController.editPhoneNumber(command);
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
            SellerController.editBrand(command);
            System.out.println("New brand name submitted !");
        } catch (InstanceAlreadyExistsException e) {
            System.out.println("This is your old brand name ! ");
            getBrand();
        }
    }

}
