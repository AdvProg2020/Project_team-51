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

}
