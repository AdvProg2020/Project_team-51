package main;

import com.jfoenix.controls.JFXButton;
import control.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.ItemOfOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartDialogController extends AnchorPane {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane cartPane;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<ItemOfOrder, String> productIdColumn;

    @FXML
    private TableColumn<ItemOfOrder, String> nameColumn;

    @FXML
    private TableColumn<ItemOfOrder, String> priceColumn;

    @FXML
    private TableColumn<ItemOfOrder, TextField> quantityColumn;

    @FXML
    private TableColumn<ItemOfOrder, String> amountColumn;


    @FXML
    private Label priceLabel;

    @FXML
    private JFXButton payButton;

    private StackPane stackPane;

    private List<ItemOfOrder> cart = new ArrayList<>();
    private String quantity;


    public CartDialogController(StackPane stackPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.stackPane = stackPane;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        cart = Controller.getCart();
        initialize();
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }

    private void initialize() {
        double price = 0;
        double priceAfterDiscount = 0;
        for (ItemOfOrder itemOfOrder : cart) {
            productIdColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getProduct().getProductId()));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getProduct().getName()));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getProduct().getPriceForSeller(itemOfOrder.getSeller()) + " $"));
            quantityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemOfOrder, TextField>, ObservableValue<TextField>>() {
                @Override
                public ObservableValue<TextField> call(TableColumn.CellDataFeatures<ItemOfOrder, TextField> param) {
                    var filed = new TextField(String.valueOf(itemOfOrder.getQuantity()));
                    filed.textProperty().addListener((a, b, newValue) -> {
                        if (newValue.matches("\\d+")) {
                            quantity = newValue;
                            itemOfOrder.setQuantity(Integer.parseInt(quantity));
                            updatePrices();
                        }
                    });
                    return (ObservableValue<TextField>) filed;
                }
            });
            amountColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getTotalPrice() + " $"));
            price += itemOfOrder.getTotalPrice();
            priceAfterDiscount += itemOfOrder.getTotalPriceWithDiscount();
        }

        priceLabel.textProperty().bind(new SimpleStringProperty(price + " $" + "     " + priceAfterDiscount + " $"));
    }

    private void updatePrices() {
        double price = 0;
        double priceAfterDiscount = 0;
        for (ItemOfOrder itemOfOrder : cart) {
            price += itemOfOrder.getTotalPrice();
            priceAfterDiscount += itemOfOrder.getTotalPriceWithDiscount();
        }
        priceLabel.textProperty().bind(new SimpleStringProperty(price + " $" + "     " + priceAfterDiscount + " $"));
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public ScrollPane getCartPane() {
        return cartPane;
    }

    public void setCartPane(ScrollPane cartPane) {
        this.cartPane = cartPane;
    }

    public TableView<?> getTable() {
        return table;
    }

    public void setTable(TableView<?> table) {
        this.table = table;
    }

    public TableColumn<ItemOfOrder, String> getProductIdColumn() {
        return productIdColumn;
    }

    public void setProductIdColumn(TableColumn<ItemOfOrder, String> productIdColumn) {
        this.productIdColumn = productIdColumn;
    }

    public TableColumn<ItemOfOrder, String> getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(TableColumn<ItemOfOrder, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    public TableColumn<ItemOfOrder, String> getPriceColumn() {
        return priceColumn;
    }

    public void setPriceColumn(TableColumn<ItemOfOrder, String> priceColumn) {
        this.priceColumn = priceColumn;
    }


    public TableColumn<ItemOfOrder, String> getAmountColumn() {
        return amountColumn;
    }

    public void setAmountColumn(TableColumn<ItemOfOrder, String> amountColumn) {
        this.amountColumn = amountColumn;
    }

    public Label getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(Label priceLabel) {
        this.priceLabel = priceLabel;
    }

    public JFXButton getPayButton() {
        return payButton;
    }

    public void setPayButton(JFXButton payButton) {
        this.payButton = payButton;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public List<ItemOfOrder> getCart() {
        return cart;
    }

    public void setCart(List<ItemOfOrder> cart) {
        this.cart = cart;
    }


}
