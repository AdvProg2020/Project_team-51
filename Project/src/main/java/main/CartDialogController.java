package main;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class CartDialogController extends AnchorPane {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane cartPane;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> productIdColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TableColumn<?, ?> removeColumn;

    @FXML
    private Label priceLabel;

    @FXML
    private JFXButton payButton;

    private StackPane stackPane;


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

    public TableColumn<?, ?> getProductIdColumn() {
        return productIdColumn;
    }

    public void setProductIdColumn(TableColumn<?, ?> productIdColumn) {
        this.productIdColumn = productIdColumn;
    }

    public TableColumn<?, ?> getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(TableColumn<?, ?> nameColumn) {
        this.nameColumn = nameColumn;
    }

    public TableColumn<?, ?> getPriceColumn() {
        return priceColumn;
    }

    public void setPriceColumn(TableColumn<?, ?> priceColumn) {
        this.priceColumn = priceColumn;
    }

    public TableColumn<?, ?> getQuantityColumn() {
        return quantityColumn;
    }

    public void setQuantityColumn(TableColumn<?, ?> quantityColumn) {
        this.quantityColumn = quantityColumn;
    }

    public TableColumn<?, ?> getAmountColumn() {
        return amountColumn;
    }

    public void setAmountColumn(TableColumn<?, ?> amountColumn) {
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
}
