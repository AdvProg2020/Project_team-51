package model;

import model.People.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    private String productId;
    private ArrayList<Seller> sellersForThisProduct = new ArrayList<Seller>();
    private Status status;
    private String name;
    private String brandName;
    private Double price;
    private Integer quantity;
    private Category parentCategory;
    private String description;
    private HashMap<String,Attributes> attributes= new HashMap<String, Attributes>();
    private ArrayList<Rate> rating;
    private ArrayList<Comment> comments;

    public Product(String productId, String name, String brandName,
                   Double price,Seller seller , Integer quantity, Category parentCategory, String description) {
        this.productId = productId;
        this.name = name;
        this.brandName = brandName;
        this.price = price;
        this.quantity = quantity;
        this.parentCategory = parentCategory;
        this.description = description;
        sellersForThisProduct.add(seller);
        allProducts.add(this);
    }

    public void addAttribute(String value , String Attribute){

    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static void setAllProducts(ArrayList<Product> allProducts) {
        Product.allProducts = allProducts;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ArrayList<Seller> getSellersForThisProduct() {
        return sellersForThisProduct;
    }

    public void setSellersForThisProduct(ArrayList<Seller> sellersForThisProduct) {
        this.sellersForThisProduct = sellersForThisProduct;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Rate> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Rate> rating) {
        this.rating = rating;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public Product getProductById(String productID){
        for (Product product : allProducts) {
            if (product.productId.equals(productID))
                return product;
        }

        return null;
    }
}
