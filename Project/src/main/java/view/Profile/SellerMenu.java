package view.Profile;

import control.SellerController;
import view.AllPatterns;
import view.Menu;
import view.MenusPattern;

public class SellerMenu extends Menu {

    private SellerController sellerController ;

    public SellerMenu(Menu parentMenu ,SellerController sellerController) {
        super("Seller Menu", parentMenu);
        this.sellerController = sellerController;
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
            viewPersonalInfo();
        } else if (command.matches(AllPatterns.COMPANY_INFO.getRegex())){

        } else if (command.matches(AllPatterns.SALES_HISTORY.getRegex())){

        } else if (command.matches(AllPatterns.MANAGE_PRODUCTS.getRegex())){

        } else if (command.matches(AllPatterns.SHOW_CATEGORIES.getRegex())){

        } else if (command.matches(AllPatterns.VIEW_OFFS.getRegex())){

        } else if (command.matches(AllPatterns.BALANCE.getRegex())){

        } else if (command.matches(AllPatterns.BACK.getRegex())){
            back();
        } else if (command.matches(AllPatterns.LOGIN.getRegex())){
            login();
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

    }

    private void viewSalesHistory() {

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

    }

    private void viewOffs(){

    }

    private void viewOffById(String id){

    }

    private void editOffAttribute(String id){

    }

    private void addOff(){

    }

    private void viewSellerBalance(){

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

                } else if (command.matches(AllPatterns.LAST_NAME.getRegex())){

                } else if (command.matches(AllPatterns.EMAIL.getRegex())){

                } else if (command.matches(AllPatterns.PHONE.getRegex())){

                } else if (command.matches(AllPatterns.BRAND.getRegex())){

                } else if (command.matches(AllPatterns.BACK.getRegex())){
                    back();
                } else if (command.matches(AllPatterns.LOGOUT.getRegex())){
                    logout();
                }
            }
        };
    }

    private void getFistName(){
        System.out.println("Please Enter Your New First Name : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+");
        sellerController.editFirstName(command);
    }

    private void getLastName(){
        System.out.println("Please Enter Your New Last Name : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+");
        sellerController.editLastName(command);
    }

    private void getEmail(){
        System.out.println("Please Enter Your New Email : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+@\\w+\\.\\w+");
        sellerController.editEmail(command);
    }

    private void getPhone(){
        System.out.println("Please Enter Your New Phone Number : ");
        command=inputInFormat("Invalid Format !" , "(?i)[0-9]+");
        sellerController.editPhoneNumber(command);
    }

    private void getBrand(){
        System.out.println("Please Enter Your New Brand Name : ");
        command=inputInFormat("Invalid Format !" , "(?i)\\w+");
        sellerController.editBrand(command);
    }





}
