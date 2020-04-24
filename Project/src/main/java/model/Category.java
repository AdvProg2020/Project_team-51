package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category {

    private String name;
    private Map<Integer,Category> subCategories = new HashMap<Integer, Category>();
    private Category parentCategory;
    private ArrayList<Product> categoryProducts = new ArrayList<Product>();
    private HashMap<String,Attributes> attributes= new HashMap<String, Attributes>();

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public void addAttribute(String value , String Attribute){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Category> getSubCategories() {
        return subCategories;
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
}
