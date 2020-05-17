package model;

import java.util.*;

public class Category {

    private String name;
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private Map<Integer,Category> subCategories = new HashMap<Integer, Category>();
    private Category parentCategory;
    private ArrayList<Product> categoryProducts = new ArrayList<Product>();
    private List<Attributes> attributes= new ArrayList<>();

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
        allCategories.add(this);
    }

    public void addAttribute(String value , String Attribute){

    }

    public static String getPathOfCategory(Category category){
        Stack<String> address = new Stack<>();
        var tempCategory = category;
        while (tempCategory!=null){
            address.push(tempCategory.getName());
            tempCategory = tempCategory.getParentCategory();
        }

        StringBuilder path = new StringBuilder("");
        path.append(address.pop());
        while (!address.isEmpty()){
            path.append(" > ");
            path.append(address.pop());
        }

        return path.toString();
    }

    public Map<Integer, Category> getSubCategories() {
        return subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubCategories(Map<Integer, Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public ArrayList<Product> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(ArrayList<Product> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static void addCategory(Category category){
        allCategories.add(category);
    }
}
