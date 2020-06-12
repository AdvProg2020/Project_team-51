package model;

import control.Controller;
import control.Exceptions.InvalidProductIdException;
import javafx.scene.image.ImageView;
import model.People.Customer;
import model.People.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {

    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    String uselessString;
    private String productId;
    private ArrayList<Seller> sellersForThisProduct = new ArrayList<Seller>();
    private Map<Seller, Status> status;
    private String name;
    private String brandName;
    private Map<Seller, Double> price = new HashMap<>();
    private Map<Seller, Integer> quantity = new HashMap<>();
    private Category parentCategory;
    private String description;
    private Map<Attributes, String> attributes = new HashMap<>();
    private List<Rate> rating = new ArrayList<>();
    private int views;
    private List<Comment> comments = new ArrayList<>();
    private ImageView imageView;


    public Product(String productId, String name, String brandName,
                   Double price, Seller seller, int quantity, Category parentCategory, String description,
                   Map<Attributes, String> attributes) {
        this.productId = productId;
        this.name = name;
        this.brandName = brandName;
        this.price.putIfAbsent(seller, price);
        this.quantity.putIfAbsent(seller, quantity);
        this.parentCategory = parentCategory;
        this.description = description;
        this.attributes = attributes;
        this.status.putIfAbsent(seller, Status.PENDING_CREATE);
        sellersForThisProduct.add(seller);
        allProducts.add(this);
        views = 0;
        uselessString = "";
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static void setAllProducts(ArrayList<Product> allProducts) {
        Product.allProducts = allProducts;
    }

    public static Product getProductById(String productID) throws InvalidProductIdException {
        for (Product product : allProducts) {
            if (product.productId.equals(productID))
                return product;
        }
        throw new InvalidProductIdException();
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public String getUselessString() {
        return uselessString;
    }

    public Map<Attributes, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Attributes, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String value, String Attribute) {

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

    public Map<Seller, Status> getStatus() {
        return status;
    }

    public void setStatus(Status status, Seller seller) {
        this.status.replace(seller, status);
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

    public Double getPriceForSeller(Seller seller) {
        return price.get(seller);
    }

    public void setPrice(Double price, Seller seller) {
        this.price.replace(seller, price);
    }

    public int getQuantityForSeller(Seller seller) {
        return quantity.get(seller);
    }

    public void setQuantity(int quantity, Seller seller) {
        this.quantity.replace(seller, quantity);
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

    public List<Rate> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Rate> rating) {
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public double averageRate() {
        return (rating.stream().map(r -> r.getScore()).reduce((a, b) -> a + b).orElse(0)) / rating.size();
    }

    public int getViews() {
        return views;
    }

    public void addView() {
        views++;
    }

    public Map<Seller, Double> getPrice() {
        return price;
    }

    public void addPriceOfNewSeller(Seller seller, double price) {
        this.price.putIfAbsent(seller, price);
    }

    public void addQuantityOfNewSeller(Seller seller, int quantity) {
        this.quantity.putIfAbsent(seller, quantity);
    }

    public Map<Seller, Integer> getQuantity() {
        return quantity;
    }

    public double getAveragePrice() {
        double total = price.values().stream().reduce(0.00, Double::sum);
        return total / price.size();
    }

    public void addRate(int rate) {
        rating.add(new Rate((Customer) Controller.getCurrentAccount(), this, rate));
    }

    public int getTotalQuantity() {
        return quantity.values().stream().reduce(0, Integer::sum);
    }

    @Override
    public String toString() {
        return "productId : " + productId + '\'' +
                " ,name : " + name + '\'' +
                " ,price : " + price +
                " ,rate : " + this.averageRate() +
                " ,quantity : " + quantity;
    }
}
