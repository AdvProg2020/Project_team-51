package control;

import model.People.Account;
import model.Requests.Request;

public class ManagerController extends Controller {
    public ManagerController(Account currentAccount) {
        super(currentAccount);
    }

    public static Boolean isThisFieldValid(String field) {
        return true;
    }

    public static void editFirstName (String firstName) {

    }

    public static void editLastName (String lastName) {

    }

    public static void editEmail (String email) {

    }

    public static void editPhoneNumber (String phoneNumber) {

    }

    public static void deleteUser (String username) {

    }

    public static void createManager (String[] info){

    }
    public static Boolean isHeAbleToCreateManger(){
        return true;
    }

    public static Boolean isThisPidValid(String productId){
        return true;
    }

    public static void removeProduct(String productId){

    }

    public static Boolean isThisCodeValid (String code){
        return true;
    }

    public static void createDiscountCode(String [] info){

    }

    public static void editDiscountCode(String[] info){

    }

    public static void removeDiscountCode(String code){

    }

    public static Boolean isRequestValid(String requestId){
        return null;
    }

    public static Request getRequestDetail(String requestId){
        return null;
    }

    public static void acceptRequest(String requestId){

    }

    public static void rejectRequest(String requestId){

    }

    public static Boolean isCategoryValid(String category){
        return null;
    }

    public static void addCategory(String[] info){

    }

    public static void editCategory(String [] info){

    }

    public static void  removeCategory(String category){

    }

}
