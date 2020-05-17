package model;

import control.Exceptions.InvalidProductIdException;
import model.People.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product {

    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    private String productId;
    private ArrayList<Seller> sellersForThisProduct = new ArrayList<Seller>();
    private Status status;
    private String name;
    private String brandName;
    private Double price;
    private int quantity;
    private Category parentCategory;
    private String description;
    private Map<Attributes , String> attributes= new HashMap<>();
    private ArrayList<Rate> rating;
    private int views;
    private ArrayList<Comment> comments;


    public Product(String productId, String name, String brandName,
                   Double price,Seller seller , int quantity, Category parentCategory, String description ,
                   Map<Attributes,String> attributes) {
        this.productId = productId;
        this.name = name;
        this.brandName = brandName;
        this.price = price;
        this.quantity = quantity;
        this.parentCategory = parentCategory;
        this.description = description;
        this.attributes=attributes;
        sellersForThisProduct.add(seller);
        allProducts.add(this);
        views = 0 ;

    }

    public Map<Attributes,String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Attributes, String> attributes) {
        this.attributes = attributes;
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

    public void addSellerForThisProduct(Seller seller) {
        sellersForThisProduct.add(seller);
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

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public static Product getProductById(String productID) throws InvalidProductIdException {
        for (Product product : allProducts) {
            if (product.productId.equals(productID))
                return product;
        }
        throw new InvalidProductIdException();
    }

    public double averageRate(){
        return   (double) ((rating.stream().map(r -> r.getScore()).reduce((a,b) -> a+b).orElse(0)) / rating.size()) ;
    }

    public int getViews() {
        return views;
    }

    public void addView(){
        views++;
    }

    public static void addProduct(Product product){
        allProducts.add(product);
    }

    @Override
    public String toString() {
        return "productId : " + productId + '\'' +
                " ,name : " + name + '\'' +
                " ,price : " + price +
                " ,rate : " + this.averageRate() +
                " ,quantity : " + quantity ;
    }
}
