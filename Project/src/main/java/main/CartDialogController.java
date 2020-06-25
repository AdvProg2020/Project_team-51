package main;

import com.jfoenix.controls.JFXButton;
import control.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
    private TableColumn<ItemOfOrder, String> quantityColumn;

    @FXML
    private TableColumn<ItemOfOrder, String> amountColumn;

    @FXML
    private TableColumn<?, ?> removeColumn;

    @FXML
    private Label priceLabel;

    @FXML
    private JFXButton payButton;

    private StackPane stackPane;

    private List<ItemOfOrder> cart = new ArrayList<>();


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


    private void initialize() {
        double price = 0;
        double priceAfterDiscount = 0;
        for (ItemOfOrder itemOfOrder : cart) {
            productIdColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getProduct().getProductId()));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getProduct().getName()));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getProduct().getPriceForSeller(itemOfOrder.getSeller()) + " $"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getQuantity() + ""));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>(itemOfOrder.getTotalPrice() + " $"));
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

    public TableColumn<ItemOfOrder, String> getQuantityColumn() {
        return quantityColumn;
    }

    public void setQuantityColumn(TableColumn<ItemOfOrder, String> quantityColumn) {
        this.quantityColumn = quantityColumn;
    }

    public TableColumn<ItemOfOrder, String> getAmountColumn() {
        return amountColumn;
    }

    public void setAmountColumn(TableColumn<ItemOfOrder, String> amountColumn) {
        this.amountColumn = amountColumn;
    }

    public TableColumn<?, ?> getRemoveColumn() {
        return removeColumn;
    }

    public void setRemoveColumn(TableColumn<?, ?> removeColumn) {
        this.removeColumn = removeColumn;
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
