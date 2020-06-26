package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.Exceptions.HaveNotLoggedInException;
import control.Filters.SearchFilter;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Products.ProductsController;
import model.Category;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private List<Product> bestSellerProducts = new ArrayList<>();
    private List<Product> mostViewedProducts = new ArrayList<>();

    private String search;

    private List<Category> allCategories;


    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton homeButton;

    @FXML
    private MenuButton categoriesButton;

    @FXML
    private TreeView categoriesTreeView;

    @FXML
    private JFXButton cartButton;

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
    private BorderPane mainPane;

    private Stage stage = new Stage();

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    @FXML
    public void initialize() {
        stackPane.setOpacity(0);
        fadeIn();
        var cartDialog = new CartDialogController(stackPane);
        var addressDialog = new AddressController(stackPane);
        var offCodeDialog = new TakeOffCodeController(stackPane);
        var paymentDialog = new PaymentDialogController(stackPane);

        bestSellerProducts = Product.getBestSellerProducts();
        mostViewedProducts = Product.getMostViewedProducts();
        allCategories = Category.getAllCategories();
        stage = Main.getPrimaryStage();

        if (!bestSellerProducts.isEmpty())
            initializeBestSellers();
        if (!mostViewedProducts.isEmpty())
            initializeMostViewed();

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search = newValue;
            }
        });

        cartButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
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
            new Thread(() -> playAudio("button.wav")).start();
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
            new Thread(() -> playAudio("button.wav")).start();
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
            new Thread(() -> playAudio("button.wav")).start();
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
            new Thread(() -> playAudio("button.wav")).start();
            try {
                Main.setRoot("main");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        //Populate categories
        Category root = allCategories.stream().filter(c -> c.getParentCategory() == null).findAny().orElse(null);
        categoriesTreeView = new TreeView<String>(populateCategories(root, new TreeItem<String>("Main")));
        categoriesTreeView.getSelectionModel().selectionModeProperty().addListener((observable, oldValue, newValue) -> {
            stage.setScene(new Scene(new ProductsController(Category.getCategoryByName(newValue.toString()))));
        });
        EventHandler<MouseEvent> mouseEventHandle = this::handleMouseClicked;
        categoriesTreeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
        customItem.setHideOnClick(false);
        customItem.setContent(categoriesTreeView);
        categoriesTreeView.setShowRoot(false);
        dashboard.setOnMouseClicked(e -> {
            if (!Controller.isLoggedIn()) {
                BoxBlur boxBlur = new BoxBlur(6, 6, 6);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
                dialogLayout.setActions(new LoginDialog(stackPane));
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
//                        Main.setRoot("customer-dashboard");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (currentAccount instanceof Seller) {
                    try {
                        fadeOut(FXMLLoader.load(Main.class.getResource("seller-dashboard.fxml")));
//                        Main.setRoot("seller-dashboard");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (currentAccount instanceof Manager) {
                    try {
                        fadeOut(FXMLLoader.load(Main.class.getResource("manager-dashboard.fxml")));
//                        Main.setRoot("manager-dashboard");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        searchButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
            stackPane.getScene().setRoot(new ProductsController(SearchFilter.getInstance().applyFilter(Product.getAllProducts(), search)));

        });

        logout.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
            if (Controller.isLoggedIn()) {
                BoxBlur boxBlur = new BoxBlur(4, 4, 4);
                JFXButton button = new JFXButton("  Yes  ");
                button.setStyle("-fx-background-color:#fe615a; -fx-background-radius:  18; -fx-text-fill: white");
                button.setPadding(new Insets(3, 16, 3, 16));
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                dialogLayout.setHeading(new Label(" Logout "));
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
    }

    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            String name = (String) ((TreeItem) categoriesTreeView.getSelectionModel().getSelectedItem()).getValue();
            Category category = Category.getCategoryByName(name);
            stackPane.getScene().setRoot(new ProductsController(category));
        }
    }

    private void fadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(stackPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void fadeOut(StackPane node) {
        node.getStylesheets().add("main.css");
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(stackPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> {
            new Thread(() -> playAudio("dialog.wav")).start();
            stackPane.getScene().setRoot(node);
        });
        fadeTransition.play();
    }

    private TreeItem<String> populateCategories(Category parent, TreeItem parentItem) {

        for (Category category : allCategories) {
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
