package main.ProductPage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.Exceptions.HaveNotLoggedInException;
import control.Exceptions.InvalidUsernameException;
import control.Exceptions.LackOfProductException;
import control.Exceptions.NotAllowedActivityException;
import control.Filters.SearchFilter;
import control.SingleProductController;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.*;
import main.Products.ProductsController;
import model.Category;
import model.Comment;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProductPageController extends StackPane {


    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton homeButton;

    @FXML
    private MenuButton categoriesButton;

    @FXML
    private JFXButton cartButton;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productDescription;

    @FXML
    private ComboBox<Seller> sellerComboBox;

    @FXML
    private Label productPrice;

    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXButton addButton;

    @FXML
    private VBox commentsBox;

    @FXML
    private Label comment;

    @FXML
    private Label offPercent;

    @FXML
    private ImageView rateStar;

    @FXML
    private Label attributesLabel;

    @FXML
    private CustomMenuItem customItem;

    @FXML
    private TreeView categoriesTreeView;


    private Product product;
    private List<Comment> allComments;
    private JFXButton addComment;
    private SingleProductController controller;
    private String quantityText;
    private String search;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXButton searchButton;


    public ProductPageController(Product product) {
        this.product = product;
        this.controller = new SingleProductController(Controller.getCurrentAccount(), product);
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    public void initialize() {
        var cartDialog = new CartDialogController(stackPane);
        var addressDialog = new AddressController(stackPane);
        var offCodeDialog = new TakeOffCodeController(stackPane);
        var paymentDialog = new PaymentDialogController(stackPane);
        allComments = controller.getComments();
        initializeProduct();
        initializeComments();

        addButton.setOnMouseClicked(e -> {
            new Thread(() -> playAudio("button.wav")).start();
            try {
                controller.addToCart(sellerComboBox.getSelectionModel().getSelectedItem().getUsername());
            } catch (LackOfProductException ex) {
                showError("Lack Of Products");
            } catch (NotAllowedActivityException ex) {
                showError("You are not allowed to do that!");
            } catch (InvalidUsernameException ex) {
            }
        });
        addComment.setOnMouseClicked(e -> {
            new Thread(() -> playAudio("button.wav")).start();
            BoxBlur boxBlur = new BoxBlur(6, 6, 6);
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            dialogLayout.setActions(new NewCommentDialog(stackPane, controller));
            dialog.show();
            mainPane.setEffect(boxBlur);
            dialog.setOnDialogClosed((JFXDialogEvent event) -> mainPane.setEffect(null));
            dialog.overlayCloseProperty().bindBidirectional(new SimpleBooleanProperty(!Controller.isLoggedIn()));
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

        Category root = Category.getAllCategories().stream().filter(c -> c.getParentCategory() == null).findAny().orElse(null);
        categoriesTreeView = new TreeView<String>(populateCategories(root, new TreeItem<String>("Main")));
        categoriesTreeView.getSelectionModel().selectionModeProperty().addListener((observable, oldValue, newValue) -> {
            stackPane.getScene().setRoot(new ProductsController(Category.getCategoryByName(newValue.toString())));
        });
        EventHandler<MouseEvent> mouseEventHandle = this::handleMouseClicked;
        categoriesTreeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
        customItem.setHideOnClick(false);
        customItem.setContent(categoriesTreeView);
        categoriesTreeView.setShowRoot(false);
        dashboard.setOnMouseClicked(e -> {
            new Thread(() -> playAudio("button.wav")).start();
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

        searchButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
            stackPane.getScene().setRoot(new ProductsController(SearchFilter.getInstance().applyFilter(Product.getAllProducts(), search)));
        });

        homeButton.setOnMouseClicked(event -> {
            new Thread(() -> playAudio("button.wav")).start();
            try {
                Main.setRoot("main");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logout.setOnMouseClicked(event -> {
            if (Controller.isLoggedIn()) {
                new Thread(() -> playAudio("button.wav")).start();
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
            new Thread(() -> playAudio("dialoge.wav")).start();
            stackPane.getScene().setRoot(node);
        });
        fadeTransition.play();
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

    private void initializeProduct() {
        productImage.setImage(product.getImage());
        productName.setText(product.getName());
        try {
            productDescription.setText(controller.digest());
        } catch (InvalidUsernameException e) {
        }
        productPrice.setText(product.getAveragePrice() + " $");

        var sellers = product.getPrice();
        for (Map.Entry<Seller, Double> seller : sellers.entrySet()) {
            sellerComboBox.getItems().add(seller.getKey());
        }

        sellerComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Seller>() {
            @Override
            public void changed(ObservableValue<? extends Seller> observable, Seller oldValue, Seller newValue) {
                productPrice.setText(newValue.getBrandName());
            }
        });
        quantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                quantityText = newValue;
            }
        });

        int rate = (int) product.averageRate();
        String attributeText = "";
        for (String attributes : controller.showAttributes().values()) {
            attributeText += attributes + ", ";
        }

        attributesLabel.setText(attributeText);

        switch (rate) {
            case 0:
                rateStar.setImage(new Image(String.valueOf(Main.class.getResource("0star.png"))));
            case 1:
                rateStar.setImage(new Image(String.valueOf(Main.class.getResource("1star.png"))));
            case 2:
                rateStar.setImage(new Image(String.valueOf(Main.class.getResource("2star.png"))));
            case 3:
                rateStar.setImage(new Image(String.valueOf(Main.class.getResource("3star.png"))));
            case 4:
                rateStar.setImage(new Image(String.valueOf(Main.class.getResource("4star.png"))));
            case 5:
                rateStar.setImage(new Image(String.valueOf(Main.class.getResource("5star.png"))));
        }


    }

    private void initializeComments() {

        for (Comment comment : allComments) {
            CommentController commentController = new CommentController();
            commentsBox.getChildren().add(commentController);
            commentController.getTitle().setText(comment.getTitle());
            commentController.getComment().setText(comment.getContext());
//            commentController.getRate().setImage("STH");
        }

        commentsBox.getChildren().add(addButton);
        addButton.setText("Add");
        addButton.setStyle("-fx-background-color: #4a804a; -fx-background-radius: 15");

    }

    private void showError(String message) {
        showErrorWithColor(message, "#fe615a");
    }

    @FXML
    private void showErrorWithColor(String message, String color) {
        new Thread(() -> playAudio("dialog.wav")).start();
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton(" OK ");
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

    @FXML
    private void showConfirmation(String message) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #6d8040; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(118,160,93,0.64)");
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

