package control;

import control.Exceptions.NoCategoriesFoundException;
import control.Filters.Filter;
import model.Category;
import model.FilterTypes;
import model.People.Account;
import model.Product;
import model.SortTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProductController extends Controller {


    private static Category category ;
    private static ArrayList<FilterTypes> availableFilter =
            new ArrayList<>(Arrays.stream(FilterTypes.values()).collect(Collectors.toList()));
    private static ArrayList<FilterTypes> appliedFilters = new ArrayList<>();
    private static SortTypes currentSort = SortTypes.VIEW_SORT ;

    public ProductController(Account currentAccount) {
        super(currentAccount);
    }

    public static ArrayList<Product> showProductsOfThisCategory(Category category){

        ArrayList<Product> products;
        try {
            var subCategories = ProductController.getSubCategories(category);
            products = new ArrayList<>(category.getCategoryProducts());
            for (Category subCategory : subCategories) {
                showProductsOfThisCategory(subCategory);
            }
        } catch (NoCategoriesFoundException e) {
            return category.getCategoryProducts();
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

    public static SortTypes getCurrentSort() {
        return currentSort;
    }

    public static ArrayList<FilterTypes> getAppliedFilters() {
        return appliedFilters;
    }

    public static void addAppliedFilters(FilterTypes filter) {
        appliedFilters.add(filter);
    }

    public static void removeAppliedFilters(Filter filter) {
        appliedFilters.remove(filter);
    }

    public static ArrayList<FilterTypes> getAvailableFilter() {
        return availableFilter;
    }

    public static ArrayList<Category> getSubCategories(Category parentCategory) throws NoCategoriesFoundException {
        ArrayList<Category> subCategories ;
        try {
            subCategories = new ArrayList<>(Category.getAllCategories().stream().filter(c -> c.getParentCategory()
                    .equals(parentCategory)).collect(Collectors.toList()));
        } catch (NullPointerException e){
            subCategories = null ;
        }
        if (subCategories==null)
            throw new NoCategoriesFoundException("There is no category");
        return subCategories;
    }
}
