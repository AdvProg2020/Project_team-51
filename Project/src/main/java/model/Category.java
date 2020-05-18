package model;

import java.util.*;

public class Category {

    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private Map<Integer, Category> subCategories = new LinkedHashMap<>();
    private Category parentCategory;
    private List<Product> categoryProducts = new ArrayList<>();
    private List<Attributes> attributes = new ArrayList<>();

    public Category(String name, Category parentCategory, Attributes... attributes) {
        this.name = name;
        this.parentCategory = parentCategory;
        allCategories.add(this);
        this.attributes = List.of(attributes);
    }

    public static String getPathOfCategory(Category category) {
        Stack<String> address = new Stack<>();
        var tempCategory = category;
        while (tempCategory != null) {
            address.push(tempCategory.getName());
            tempCategory = tempCategory.getParentCategory();
        }

        StringBuilder path = new StringBuilder();
        path.append(address.pop());
        while (!address.isEmpty()) {
            path.append(" > ");
            path.append(address.pop());
        }

        return path.toString();
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static void addCategory(Category category) {
        allCategories.add(category);
    }

    public List<Attributes> getAttributes() {
        return attributes;
    }

    public Map<Integer, Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Map<Integer, Category> subCategories) {
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Product> getCategoryProducts() {
        return categoryProducts;
    }
}
