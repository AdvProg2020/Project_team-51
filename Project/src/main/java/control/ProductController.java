package control;

import model.Category;
import model.Filter;
import model.People.Account;
import model.Product;
import model.Sort;

import java.util.ArrayList;

public class ProductController extends Controller {

    private Category category ;
    private ArrayList<Sort> availableSorts = new ArrayList<Sort>();
    private ArrayList<Filter> availableFilter = new ArrayList<Filter>();
    private Sort currentSort ;

    public ProductController(Account currentAccount) {
        super(currentAccount);
    }

    public static ArrayList<String> viewCategories() {
        return null;
    }

    public static ArrayList<String> showAvailableFilters() {
        return null;
    }

    public static void applyFilter(String filter){

    }

    public static ArrayList<String> currentFilters() {
        return null;
    }

    public static void disableFilter(String filter){

    }


    public static ArrayList<String> showAvailableSort() {
        return null;
    }

    public static void applySort(String sort){

    }

    public static String currentSort(){
        return null;
    }

    public static void disableSort(){

    }

    public static ArrayList<String> showProducts() {
        return null;
    }

    public static Product showProduct() {
        return null;
    }


}
