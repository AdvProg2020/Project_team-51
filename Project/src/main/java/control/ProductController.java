package control;

import model.Category;
import model.Filters.Filter;
import model.People.Account;
import model.Product;
import model.Sorts.Sort;

import java.util.ArrayList;
import java.util.Map;

public class ProductController extends Controller {


    private static Category category ;
    private static ArrayList<Filter> availableFilter = new ArrayList<>();
    private static ArrayList<Filter> appliedFilters = new ArrayList<>();
    private static Sort currentSort ;

    public ProductController(Account currentAccount) {
        super(currentAccount);
    }

    public static ArrayList<Product> showProductsOfThisCategory(Category category){
        ArrayList<Product> products = new ArrayList<>();
        if (category.getSubCategories()==null)
            return category.getCategoryProducts();

        products.addAll(category.getCategoryProducts());

        for (Map.Entry<Integer, Category> categoryEntry : category.getSubCategories().entrySet()) {
            products.addAll(showProductsOfThisCategory(categoryEntry.getValue()));
        }

        return  products;
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

    public  static Category getCategory() {
        return category;
    }

    public static void setCategory(Category category) {
        ProductController.category = category;
    }

    public static Sort getCurrentSort() {
        return currentSort;
    }

    public static ArrayList<Filter> getAppliedFilters() {
        return appliedFilters;
    }

    public static void addAppliedFilters(Filter filter) {
        appliedFilters.add(filter);
    }

    public static void removeAppliedFilters(Filter filter) {
        appliedFilters.remove(filter);
    }

    public static ArrayList<Filter> getAvailableFilter() {
        return availableFilter;
    }
}
