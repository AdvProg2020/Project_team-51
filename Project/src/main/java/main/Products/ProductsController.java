package main.Products;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.Exceptions.HaveNotLoggedInException;
import control.Filters.AvailabilityFilter;
import control.Filters.PriceRangeFilter;
import control.Filters.RateRangeFilter;
import control.Filters.SearchFilter;
import control.ProductController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.*;
import main.ProductPage.ProductPageController;
import model.Category;
import model.Product;
import model.SortTypes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductsController extends StackPane {


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
    private ComboBox<String> comboBox;

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
    private JFXToggleButton availableFilter;

    @FXML
    private JFXToggleButton discountFilter;

    @FXML
    private TreeView<?> treeView;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane mainPane;


    private double minimumPrice;
    private double maximumPrice;
    private double minimumRate;
    private double maximumRate;

    private Stage stage;


    private Category currentCategory;
    private Stack<List<Product>> productsHistory = new Stack<>();
    private HashMap<Product, SingleProduct> allProducts = new LinkedHashMap<>();
    private List<Product> products;
    private List<String> sorts;
    private String search;


    public ProductsController(List<Product> products) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("products.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.products = products;
        categoryName.setText("");
        initialize();
    }

    public ProductsController(Category currentCategory) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("products.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            Main.setRoot((Parent) fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.currentCategory = currentCategory;
        if (currentCategory != null) {
            products = ProductController.showProductsOfThisCategory(currentCategory);
        }
        categoryName.setText(currentCategory.getName());
        initialize();
    }

    @FXML
    public void initialize() {
        stage = Main.getPrimaryStage();
        sorts = ProductController.showAvailableSort();
        initializeComponents();
        initializeSorts();
//        updateListToMap();
//        initializePageNumbers();
//        initializeCategories();
//        pageNumberUpdate(1);
    }

    private void initializeComponents() {

        var cartDialog = new CartDialogController(stackPane);
        var addressDialog = new AddressController(stackPane);
        var offCodeDialog = new TakeOffCodeController(stackPane);
        var paymentDialog = new PaymentDialogController(stackPane);

        System.out.println(dashboard);
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
            try {
                Main.setRoot("main");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search = newValue;
            }
        });
        searchButton.setOnMouseClicked(event -> {
            stage.setScene(new Scene(new ProductsController(SearchFilter.getInstance().applyFilter(products, search))));
        });

        cartButton.setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(cartDialog);
            dialogLayout.setStyle("-fx-background-color:  #db5e5c");
            dialog.show();
            mainPane.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> mainPane.setEffect(null));
        });

        cartDialog.getPayButton().setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(addressDialog);
            dialogLayout.setStyle("-fx-background-color:   #886488");
            dialog.show();
            cartDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> cartDialog.setEffect(null));
        });

        addressDialog.getNextButton().setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(offCodeDialog);
            dialogLayout.setStyle("-fx-background-color:   #f3c669");
            dialog.show();
            addressDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> addressDialog.setEffect(null));
        });

        offCodeDialog.getNextButton().setOnMouseClicked(event -> {
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(paymentDialog);
            dialogLayout.setStyle("-fx-background-color:    #b2aa72");
            dialog.show();
            offCodeDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> offCodeDialog.setEffect(null));
        });

        paymentDialog.getPayButton().setOnMouseClicked(event -> {
            stage.setScene(new Scene(new ProductsController(products)));
        });

        minPriceSlider.valueProperty().addListener((a, b, newValue) -> {
            minimumPrice = newValue.doubleValue();
            products = PriceRangeFilter.getInstance().applyFilter(products, minimumPrice, maximumPrice);
            updateListToMap();
            pageNumberUpdate(1);
        });

        maxPriceSlider.valueProperty().addListener((a, b, newValue) -> {
            maximumPrice = newValue.intValue();
            products = PriceRangeFilter.getInstance().applyFilter(products, minimumPrice, maximumPrice);
            updateListToMap();
            pageNumberUpdate(1);
        });

        minRateSlider.valueProperty().addListener((a, b, newValue) -> {
            minimumRate = newValue.intValue();
            products = RateRangeFilter.getInstance().applyFilter(products, minimumRate, maximumRate);
            updateListToMap();
            pageNumberUpdate(1);
        });

        maxRateSlider.valueProperty().addListener((a, b, newValue) -> {
            maximumRate = newValue.intValue();
            products = RateRangeFilter.getInstance().applyFilter(products, minimumRate, maximumRate);
            updateListToMap();
            pageNumberUpdate(1);
        });

        availableFilter.selectedProperty().addListener((a, b, newValue) -> {
            if (newValue) {
                products = AvailabilityFilter.getInstance().applyFilter(products);
                updateListToMap();
                pageNumberUpdate(1);
                productsHistory.push(products);
            } else {
                products = productsHistory.pop();
            }
        });

        logout.setOnMouseClicked(event -> {
            if (Controller.isLoggedIn()) {
                BoxBlur boxBlur = new BoxBlur(4, 4, 4);
                JFXButton button = new JFXButton("  OK  ");
                button.setStyle("-fx-background-color:#fe615a; -fx-background-radius:  18; -fx-text-fill: white");
                button.setPadding(new Insets(3, 16, 3, 16));
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                dialogLayout.setHeading(new Label(" You've logged out "));
                dialogLayout.setStyle("-fx-background-color: rgba(255,104,110,0.64)");
                JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                button.setOnMouseClicked((MouseEvent event1) -> {
                    dialog.close();
                });
                dialogLayout.setActions(button);
                dialog.show();
                mainPane.setEffect(boxBlur);
                dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                    mainPane.setEffect(null);
                    try {
                        Controller.logout();
                    } catch (HaveNotLoggedInException e) {
                    }
                });
            } else {
                showError(" You have not logged in yet !");
            }
        });


        discountFilter.selectedProperty().addListener((a, b, newValue) -> {
//            if (newValue) {
//                products = AvailabilityFilter.getInstance().applyFilter(products);
//            } else {
//                products = productsHistory.pop();
//            }
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
        comboBox.getItems().addAll(allSorts.stream().distinct().collect(Collectors.toList()));
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Name"))
                products = ProductController.applySort(SortTypes.NAME_SORT, products);
            else if (newValue.equals("Price"))
                products = ProductController.applySort(SortTypes.PRICE_SORT, products);
            else if (newValue.equals("View"))
                products = ProductController.applySort(SortTypes.VIEW_SORT, products);
            else if (newValue.equals("Rate"))
                products = ProductController.applySort(SortTypes.RATE_SORT, products);
            updateListToMap();
            if (!newValue.equals(oldValue))
                pageNumberUpdate(1);
        });
    }

    private void showProducts(Map<Product, SingleProduct> products) {
        productsBox.getChildren().clear();
        products.forEach((a, b) -> {
            productsBox.getChildren().add(b);
            b.getProductName().setOnMouseClicked(event -> {
                stage.setScene(new Scene(new ProductPageController()));
            });
        });
        initializePageNumbers();
    }

    private void showError(String message) {
        showErrorWithColor(message, "#fe615a");
    }

    @FXML
    private void showErrorWithColor(String message, String color) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton("  Yes  ");
        button.setStyle("-fx-background-color:" + color + "; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(255,104,110,0.64)");
        JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        mainPane.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
    }


}
