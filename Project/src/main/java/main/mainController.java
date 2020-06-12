package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import control.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class mainController {

    private List<Product> bestSellerProducts = new ArrayList<>();
    private List<Product> mostViewedProducts = new ArrayList<>();

    private String search;

    private List<Category> allCategories;


    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView dashboard;

    @FXML
    private ImageView logout;

    @FXML
    private JFXButton homeButton;

    @FXML
    private MenuButton categoriesButton;

    @FXML
    private TreeView categoriesTreeView;

    @FXML
    private JFXButton aboutUsButton;

    @FXML
    private JFXButton contactUsButton;

    @FXML
    private ImageView cartButton;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchButton;

    @FXML
    private ImageView BestSellerProductImage1;

    @FXML
    private Label BestSellerProductName1;

    @FXML
    private Label BestSellerProductPrice1;

    @FXML
    private ImageView BestSellerProductImage2;

    @FXML
    private Label BestSellerProductName2;

    @FXML
    private Label BestSellerProductPrice2;

    @FXML
    private ImageView BestSellerProductImage4;

    @FXML
    private Label BestSellerProductName4;

    @FXML
    private Label BestSellerProductPrice4;

    @FXML
    private ImageView BestSellerProductImage3;

    @FXML
    private Label BestSellerProductName3;

    @FXML
    private Label BestSellerProductPrice3;

    @FXML
    private ImageView mostViewedProductImage1;

    @FXML
    private Label mostViewedProductName1;

    @FXML
    private Label mostViewedProductPrice1;

    @FXML
    private ImageView mostViewedProductImage2;

    @FXML
    private Label mostViewedProductName2;

    @FXML
    private Label mostViewedProductIPrice2;

    @FXML
    private ImageView mostViewedProductImage4;

    @FXML
    private Label mostViewedProductName4;

    @FXML
    private Label mostViewedProductPrice4;

    @FXML
    private Label mostViewedProductPrice2;

    @FXML
    private ImageView mostViewedProductImage3;

    @FXML
    private Label mostViewedProductName3;

    @FXML
    private Label mostViewedProductPrice3;

    @FXML
    private CustomMenuItem customItem;


    @FXML
    public void initialize() {
        bestSellerProducts = Product.getBestSellerProducts();
        mostViewedProducts = Product.getMostViewedProducts();
        allCategories = Category.getAllCategories();

        if (!bestSellerProducts.isEmpty())
            initializeBestSellers();
        if (!mostViewedProducts.isEmpty())
            initializeMostViewed();

        if (Controller.getCurrentAccount() == null) {
            logout.setVisible(false);
            // call login
        }

        aboutUsButton.setOnMouseClicked((Event) -> {
            //load about us page
        });

        contactUsButton.setOnMouseClicked((Event) -> {
            //load contact us page
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search = newValue;
            }
        });

        searchButton.setOnMouseClicked(event -> {
            // implement search
        });

        cartButton.setOnMouseClicked(event -> {
            // load Cart page
        });

        //Populate categories
        Category root = allCategories.stream().findAny().filter(c -> c.getParentCategory() == null).orElse(null);
        categoriesTreeView = new TreeView<String>(populateCategories(root, new TreeItem<String>("Main")));
        customItem.setContent(categoriesTreeView);
        categoriesTreeView.setShowRoot(true);


    }


    private TreeItem<String> populateCategories(Category parent, TreeItem parentItem) {

        for (Category category : allCategories) {
            TreeItem<String> treeItem = new TreeItem(category.getName());
            parentItem.getChildren().add(treeItem);
            if (category.getSubCategories() != null)
                for (Integer subCategory : category.getSubCategories().keySet()) {
                    populateCategories(category, treeItem);
                }
        }
        return parentItem;
    }


    private void initializeBestSellers() {

        BestSellerProductImage1.setImage(bestSellerProducts.get(0).getImage());
        BestSellerProductName1.setText(bestSellerProducts.get(0).getName());
        BestSellerProductPrice1.setText(bestSellerProducts.get(0).getAveragePrice() + " $");

        BestSellerProductImage2.setImage(bestSellerProducts.get(1).getImage());
        BestSellerProductName2.setText(bestSellerProducts.get(1).getName());
        BestSellerProductPrice2.setText(bestSellerProducts.get(1).getAveragePrice() + " $");

        BestSellerProductImage3.setImage(bestSellerProducts.get(2).getImage());
        BestSellerProductName3.setText(bestSellerProducts.get(2).getName());
        BestSellerProductPrice3.setText(bestSellerProducts.get(2).getAveragePrice() + " $");

        BestSellerProductImage4.setImage(bestSellerProducts.get(3).getImage());
        BestSellerProductName4.setText(bestSellerProducts.get(3).getName());
        BestSellerProductPrice4.setText(bestSellerProducts.get(3).getAveragePrice() + " $");
    }

    private void initializeMostViewed() {

        mostViewedProductImage1.setImage(mostViewedProducts.get(0).getImage());
        mostViewedProductName1.setText(mostViewedProducts.get(0).getName());
        mostViewedProductPrice1.setText(mostViewedProducts.get(0).getAveragePrice() + " $");

        mostViewedProductImage2.setImage(mostViewedProducts.get(1).getImage());
        mostViewedProductName2.setText(mostViewedProducts.get(1).getName());
        mostViewedProductPrice2.setText(mostViewedProducts.get(1).getAveragePrice() + " $");

        mostViewedProductImage3.setImage(mostViewedProducts.get(2).getImage());
        mostViewedProductName3.setText(mostViewedProducts.get(2).getName());
        mostViewedProductPrice3.setText(mostViewedProducts.get(2).getAveragePrice() + " $");

        mostViewedProductImage4.setImage(mostViewedProducts.get(3).getImage());
        mostViewedProductName4.setText(mostViewedProducts.get(3).getName());
        mostViewedProductPrice4.setText(mostViewedProducts.get(3).getAveragePrice() + " $");
    }

}
