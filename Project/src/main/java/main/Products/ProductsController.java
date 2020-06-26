package main.Products;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.CustomerController;
import control.Exceptions.HaveNotLoggedInException;
import control.Exceptions.InsufficientBalanceException;
import control.Filters.*;
import control.ProductController;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.*;
import main.ProductPage.ProductPageController;
import model.Category;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
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
    private BorderPane mainPane;

    @FXML
    private CustomMenuItem customItem;


    private double minimumPrice;
    private double maximumPrice;
    private double minimumRate;
    private double maximumRate;

    private Stage stage;


    private Category currentCategory;
    private Stack<List<Product>> productsHistory = new Stack<>();
    private HashMap<Product, SingleProduct> allProducts = new LinkedHashMap<>();
    private List<Product> products = new ArrayList<>();
    private List<String> sorts;
    private List<Product> totalProducts = new ArrayList<>();
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
        this.getStylesheets().add(String.valueOf(Main.class.getResource("products.css")));
        this.products = products;
        totalProducts = products;
        categoryName.setText("Results");
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
            totalProducts = products;
        }
        this.getStylesheets().add(String.valueOf(Main.class.getResource("products.css")));
        categoryName.setText(currentCategory.getName());
        initialize();
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    @FXML
    public void initialize() {
        stage = Main.getPrimaryStage();
        sorts = ProductController.showAvailableSort();
        initializeComponents();
        initializeSorts();
        if (totalProducts != null)
            updateListToMap();
        if (currentCategory != null)
            initializeCategories();
        pageNumberUpdate(1);
    }

    private void initializeComponents() {

        var cartDialog = new CartDialogController(this);
        var addressDialog = new AddressController(this);
        var offCodeDialog = new TakeOffCodeController(this);
        var paymentDialog = new PaymentDialogController(this);

        System.out.println(dashboard);
        dashboard.setOnMouseClicked(e -> {
            if (!Controller.isLoggedIn()) {
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.CENTER);
                dialogLayout.setActions(new LoginDialog(this));
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
            this.getScene().setRoot(new ProductsController(SearchFilter.getInstance().applyFilter(Product.getAllProducts(), search)));
        });

        cartButton.setOnMouseClicked(event -> {
            var account = Controller.getCurrentAccount();
            if (account == null || account instanceof Customer) {
                new Thread(() -> playAudio("button.wav")).start();
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.CENTER);
                dialogLayout.setActions(cartDialog);
                dialogLayout.setStyle("-fx-background-color:  #db5e5c");
                dialog.show();
                mainPane.setEffect(boxBlur);
                dialog.setOnDialogClosed((JFXDialogEvent e) -> mainPane.setEffect(null));
            } else {
                showError("Not allowed activity");
            }
        });
        cartDialog.getPayButton().setOnMouseClicked(event -> {
            var account = Controller.getCurrentAccount();
            if (account instanceof Customer) {
                new Thread(() -> playAudio("button.wav")).start();
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.CENTER);
                dialogLayout.setActions(addressDialog);
                dialogLayout.setStyle("-fx-background-color:   #886488");
                dialog.show();
                cartDialog.setEffect(boxBlur);
                dialog.setOnDialogClosed((JFXDialogEvent e) -> cartDialog.setEffect(null));
            } else {
                showError("You have to login first!");
            }
        });
        addressDialog.getNextButton().setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(offCodeDialog);
            dialogLayout.setStyle("-fx-background-color:   #f3c669");
            dialog.show();
            addressDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> addressDialog.setEffect(null));
        });
        offCodeDialog.getNextButton().setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(paymentDialog);
            dialogLayout.setStyle("-fx-background-color:    #b2aa72");
            dialog.show();
            offCodeDialog.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent e) -> offCodeDialog.setEffect(null));
        });
        paymentDialog.getPayButton().setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
            try {
                new CustomerController(Controller.getCurrentAccount()).purchase();
                Main.setRoot("main");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InsufficientBalanceException e) {
                showError("Insufficient Money");
            }
        });

        minPriceSlider.valueProperty().addListener((a, b, newValue) -> {
            updateProducts();
        });

        maxPriceSlider.valueProperty().addListener((a, b, newValue) -> {
            updateProducts();
        });

        minRateSlider.valueProperty().addListener((a, b, newValue) -> {
            updateProducts();
        });

        maxRateSlider.valueProperty().addListener((a, b, newValue) -> {
            updateProducts();
        });

        availableFilter.selectedProperty().addListener((a, b, newValue) -> {
            updateProducts();
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
                JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.CENTER);
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

        dashboard.setOnMouseClicked(e -> {
            if (!Controller.isLoggedIn()) {
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
                dialogLayout.setActions(new LoginDialog(this));
                dialog.show();
                mainPane.setEffect(boxBlur);
                dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                    mainPane.setEffect(null);
                    if (Controller.isLoggedIn()) {
                        logout.setVisible(true);
                    }
                });
                dialog.overlayCloseProperty().bindBidirectional(new SimpleBooleanProperty(!Controller.isLoggedIn()));
            } else {
                var currentAccount = Controller.getCurrentAccount();
                if (currentAccount instanceof Customer) {
                    try {
                        fadeOut(FXMLLoader.load(Main.class.getResource("customer-dashboard.fxml")));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (currentAccount instanceof Seller) {
                    try {
                        fadeOut(FXMLLoader.load(Main.class.getResource("seller-dashboard.fxml")));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (currentAccount instanceof Manager) {
                    try {
                        fadeOut(FXMLLoader.load(Main.class.getResource("manager-dashboard.fxml")));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        discountFilter.selectedProperty().addListener((a, b, newValue) -> {
            updateProducts();
        });


        Category root = Category.getAllCategories().stream().filter(c -> c.getParentCategory() == null).findAny().orElse(null);
        categoriesTreeView = new TreeView<String>(populateCategories(root, new TreeItem<String>("Main")));
        categoriesTreeView.getSelectionModel().selectionModeProperty().addListener((observable, oldValue, newValue) -> {
            stage.setScene(new Scene(new ProductsController(Category.getCategoryByName(newValue.toString()))));
        });
        EventHandler<MouseEvent> mouseEventHandle = this::handleMouseClicked;
        categoriesTreeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
        customItem.setHideOnClick(false);
        customItem.setContent(categoriesTreeView);
        categoriesTreeView.setShowRoot(false);
    }

    private void fadeOut(StackPane node) {
        node.getStylesheets().add("main.css");
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> {
            this.getScene().setRoot(node);
        });
        fadeTransition.play();
    }

    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            String name = (String) ((TreeItem) categoriesTreeView.getSelectionModel().getSelectedItem()).getValue();
            Category category = Category.getCategoryByName(name);
            this.getScene().setRoot(new ProductsController(category));
        }
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
        allProducts.clear();
        for (Product product : products) {
            product.setImage(RandomPicture.getRandomImage());
            SingleProduct singleProduct = new SingleProduct();
            singleProduct.getProductPrice().setText(product.getAveragePrice() + " $");
            singleProduct.getProductName().setText(product.getName());
            singleProduct.getProductImage().setImage(product.getImage());
            singleProduct.getProductDescription().setText(product.getDescription());
            int number = product.getTotalQuantity();
            if (number == 0) {
                singleProduct.getProductImage().setOpacity(.4);
            }
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
        comboBox.getSelectionModel().select(0);
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
                this.getScene().setRoot(new ProductPageController(a));
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
        JFXDialog dialog = new JFXDialog(this, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        mainPane.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
    }

    private void updateProducts() {
        var products = PriceRangeFilter.getInstance().applyFilter(totalProducts, minimumPrice, maximumPrice);
        var products2 = RateRangeFilter.getInstance().applyFilter(products, minimumRate, maximumRate);
        boolean isAvailableFilterOn = availableFilter.selectedProperty().get();
        boolean isDiscountFilterOn = discountFilter.selectedProperty().get();
        var products3 = isAvailableFilterOn ? AvailabilityFilter.getInstance().applyFilter(products2) : products2;
        var products4 = isDiscountFilterOn ? DiscountFilter.getInstance().applyFilter(products3) : products3;
        String sort = comboBox.getSelectionModel().selectedItemProperty().get();
        if (sort.equals("Name"))
            products = ProductController.applySort(SortTypes.NAME_SORT, products4);
        else if (sort.equals("Price"))
            products = ProductController.applySort(SortTypes.PRICE_SORT, products4);
        else if (sort.equals("View"))
            products = ProductController.applySort(SortTypes.VIEW_SORT, products4);
        else if (sort.equals("Rate"))
            products = ProductController.applySort(SortTypes.RATE_SORT, products4);

        updateListToMap();
        pageNumberUpdate(1);
    }

}
