package control;

import control.Exceptions.NoCategoriesFoundException;
import control.Filters.*;
import model.Category;
import model.FilterTypes;
import model.People.Account;
import model.Product;
import model.SortTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController extends Controller {


    private static Category category;
    private static List<FilterTypes> availableFilter =
            new ArrayList<>(Arrays.stream(FilterTypes.values()).collect(Collectors.toList()));
    private static List<FilterTypes> appliedFilters = new ArrayList<>();
    private static SortTypes currentSort = SortTypes.VIEW_SORT;

    public ProductController(Account currentAccount) {
        super(currentAccount);
    }

    public static List<Product> showProductsOfThisCategory(Category category) throws NullPointerException {

        List<Product> products = new ArrayList<>();

        try {
            var subCategories = ProductController.getSubCategories(category);
            if (category != null)
                products.addAll(category.getCategoryProducts());
            for (Category subCategory : subCategories) {
                showProductsOfThisCategory(subCategory);
            }
        } catch (NoCategoriesFoundException e) {
            if (!category.getCategoryProducts().isEmpty())
                return category.getCategoryProducts();
        }

        return products;
    }

    public static List<String> showAvailableFilters() {
        return availableFilter.stream().map(FilterTypes::getFilter)
                .map(Filter::getName).collect(Collectors.toList());
    }

    public static List<Product> filter(List<Product> products) {
        List<Product> filteredProducts = new ArrayList<>();
        for (FilterTypes filterType : appliedFilters) {
            var filter = filterType.getFilter();
            if (filter instanceof AvailabilityFilter) {
                filteredProducts = filter.applyFilter(filteredProducts);
            } else if (filter instanceof PriceRangeFilter) {
                filteredProducts = filter.applyFilter(filteredProducts, PriceRangeFilter.getInstance().from,
                        PriceRangeFilter.getInstance().to);
            } else if (filter instanceof RateRangeFilter) {
                filteredProducts = filter.applyFilter(filteredProducts, RateRangeFilter.getInstance().from,
                        RateRangeFilter.getInstance().to);
            } else if (filter instanceof CategoryFilter) {
                filteredProducts = filter.applyFilter(filteredProducts, CategoryFilter.getInstance().acceptedCategories);
            }
        }
        return filteredProducts;
    }

    public static List<FilterTypes> currentFilters() {
        return appliedFilters;
    }

    public static void disableFilter(FilterTypes filter) {
        appliedFilters.remove(filter);
    }


    public static ArrayList<String> showAvailableSort() {
        return new ArrayList<String>(Arrays.asList("Name", "Price", "Rate", "View"));
    }

    public static List<Product> applySort(SortTypes sort, List<Product> products) {
        currentSort = sort;
        return currentSort().getSort().applySort(products, true);
    }

    public static SortTypes currentSort() {
        return currentSort;
    }

    public static void disableSort() {
        currentSort = SortTypes.VIEW_SORT;
    }

    public static ArrayList<String> showProducts() {
        return null;
    }

    public static Product showProduct() {
        return null;
    }

    public static Category getCategory() {
        return category;
    }

    public static void setCategory(Category category) {
        ProductController.category = category;
    }

    public static SortTypes getCurrentSort() {
        return currentSort;
    }

    public static List<FilterTypes> getAppliedFilters() {
        return appliedFilters;
    }

    public static void addAppliedFilters(FilterTypes filter) {
        appliedFilters.add(filter);
    }

    public static List<Category> getSubCategories(Category parentCategory) throws NoCategoriesFoundException {
        List<Category> subCategories;
        try {
            subCategories = Category.getAllCategories().stream().filter(c -> c.getParentCategory()
                    .equals(parentCategory)).collect(Collectors.toList());
        } catch (NullPointerException e) {
            subCategories = null;
        }
        if (subCategories == null)
            throw new NoCategoriesFoundException("There is no category ! ");
        return subCategories;
    }


}
