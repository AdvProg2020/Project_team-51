package main.Products;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.ProductController;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.LoginDialog;
import model.Category;
import model.Product;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsController {


    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton home;

    @FXML
    private MenuButton categoriesButton;

    @FXML
    private CustomMenuItem customMenuItem;

    @FXML
    private TreeView<?> categoriesTreeView;

    @FXML
    private JFXButton cartButton;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchButton;

    @FXML
    private Label categoryName;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private VBox productsBox;

    @FXML
    private VBox filterBox;

    @FXML
    private JFXSlider minPriceSlider;

    @FXML
    private JFXSlider maxPriceSlider;

    @FXML
    private JFXSlider minRateSlider;

    @FXML
    private JFXSlider maxRateSlider;

    @FXML
    private TreeView<?> treeView;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane mainPane;


    private Category currentCategory;

    private HashMap<Product, SingleProduct> allProducts = new LinkedHashMap<>();
    private List<Product> products;
    private List<String> sorts;
    private String search;


    @FXML
    public void initialize() {

        currentCategory = ProductController.getCategory();
        sorts = ProductController.showAvailableSort();

//        products = ProductController.showProductsOfThisCategory(currentCategory);
//        initializeComponents();
//        initializeFilters();
//        initializeSorts();
//        updateListToMap();
//        initializePageNumbers();
//        initializeCategories();
//        pageNumberUpdate(1);

    }

    private void initializeComponents() {
        dashboard.setOnMouseClicked(e -> {
            if (!Controller.isLoggedIn()) {
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                dialogLayout.setActions(new LoginDialog(stackPane));
                dialog.show();
                mainPane.setEffect(boxBlur);
                dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
                dialog.overlayCloseProperty().bindBidirectional(new SimpleBooleanProperty(!Controller.isLoggedIn()));
            }
        });
        home.setOnMouseClicked(event -> {
            //change scene to main.fxml
        });
        logout.visibleProperty().bind(new ObservableBooleanValue() {
            @Override
            public boolean get() {
                return Controller.isLoggedIn();
            }

            @Override
            public void addListener(ChangeListener<? super Boolean> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Boolean> listener) {

            }

            @Override
            public Boolean getValue() {
                return Controller.isLoggedIn();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
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
    }

    private void initializeCategories() {
        treeView = new TreeView<String>(populateCategories(currentCategory, new TreeItem<String>("Here")));
        treeView.getSelectionModel().selectionModeProperty().addListener((observable, oldValue, newValue) -> {
        });
        treeView.setShowRoot(false);
    }

    private TreeItem<String> populateCategories(Category parent, TreeItem parentItem) {
        for (Category category : Category.getAllCategories()) {
            if (category.getParentCategory() != null && category.getParentCategory().getName().equals(parent.getName())) {
                TreeItem<String> treeItem = new TreeItem(category.getName());
                parentItem.getChildren().add(treeItem);
                if (category.getSubCategories() != null)
                    for (Integer subCategory : category.getSubCategories().keySet()) {
                        if (subCategory != null)
                            populateCategories(category, treeItem);
                    }
            }
        }
        return parentItem;
    }


    private void pageNumberUpdate(int number) {
        Map<Product, SingleProduct> updatedProducts = allProducts.entrySet().stream().skip((number - 1) * 5).limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        showProducts(updatedProducts);
    }

    private void updateListToMap() {
        for (Product product : products) {
            SingleProduct singleProduct = new SingleProduct();
            singleProduct.getProductPrice().setText(product.getAveragePrice() + " $");
            singleProduct.getProductName().setText(product.getName());
            singleProduct.getProductImage().setImage(product.getImage());
            singleProduct.getProductDescription().setText(product.getDescription());
            allProducts.put(product, singleProduct);
        }
    }

    private void initializePageNumbers() {
        HBox pageNumberBox = new PageNumberHBox().getPageNumberBox();
        for (int i = 0; i < products.size() / 5 + 1; i++) {
            PageNumber pageNumber = new PageNumber();
            pageNumber.getButton().setText(String.valueOf(i + 1));
            pageNumber.getButton().setOnMouseClicked(event -> {
                pageNumberUpdate(Integer.parseInt(pageNumber.getButton().getText()));
            });
            pageNumberBox.getChildren().add(pageNumber);
        }
        productsBox.getChildren().add(pageNumberBox);
    }

    private void initializeSorts() {
        ObservableList<String> allSorts = FXCollections.observableArrayList();
        allSorts.addAll(sorts);
//        comboBox.getItems().addAll(allSorts);
//        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.equals("Name"))
//                products = ProductController.applySort(SortTypes.NAME_SORT, products);
//            else if (newValue.equals("Price"))
//                products = ProductController.applySort(SortTypes.PRICE_SORT, products);
//            else if (newValue.equals("View"))
//                products = ProductController.applySort(SortTypes.VIEW_SORT, products);
//            else if (newValue.equals("Rate"))
//                products = ProductController.applySort(SortTypes.RATE_SORT, products);
//            updateListToMap();
//            if (!newValue.equals(oldValue))
//                pageNumberUpdate(1);
//        });
    }

    private void showProducts(Map<Product, SingleProduct> products) {
        productsBox.getChildren().clear();
        products.forEach((a, b) -> {
            productsBox.getChildren().add(b);
            b.setOnMouseClicked(e -> {
                //implement
                System.out.println(a.getName());
            });
        });
        initializePageNumbers();
    }


}
