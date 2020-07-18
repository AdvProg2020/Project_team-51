package main;

import control.Controller;
import control.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ItemOfOrder;
import model.OffCode;
import model.OrderLog.Order;
import model.People.Account;
import model.People.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerMenuPanes {
    CustomerController customerController = new CustomerController(Controller.getCurrentAccount());

    public Pane getPersonalInfoPane() {
        Account currentAccount = Controller.getCurrentAccount();
        final int X = 300;
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);
        Label usernameLabel = getLabel("username", X, 60);
        Label userNameError = getErrorLabel("", X, 80);
        TextField username = getTextFieldDefault(currentAccount.getUserName(), 300, 100);
        username.setEditable(false);
        Label passwordLabel = getLabel("password", X, 150);
        Label passwordFieldError = getErrorLabel("", X, 170);
        PasswordField passwordField = new PasswordField();
        setPlace(passwordField, X, 190);
        Label confirmPasswordFieldLabel = getLabel("conirm password", X, 240);
        Label confirmPasswordFieldError = getErrorLabel("", X, 260);
        PasswordField confirmPasswordField = new PasswordField();
        setPlace(confirmPasswordField, X, 280);
        Label nameLabel = getLabel("name", X, 330);
        Label nameError = getErrorLabel("", X, 350);
        TextField nameTextField = getTextFieldDefault(currentAccount.getFirstName(), X, 370);
        Label lastNameLabel = getLabel("last name", X, 420);
        Label lastNameError = getErrorLabel("", X, 440);
        TextField lastNameTextField = getTextFieldDefault(currentAccount.getLastName(), X, 460);
        Label emailLabel = getLabel("email", X, 510);
        Label emailError = getErrorLabel("", X, 530);
        TextField emailTextField = getTextFieldDefault(currentAccount.getEmail(), X, 550);
        Label phoneNumberLabel = getLabel("phone number", X, 600);
        Label phoneNumberError = getErrorLabel("", X, 620);
        TextField phoneNumberTextField = getTextFieldDefault(currentAccount.getPhoneNumber(), X, 640);
        Label balanceLabel = getLabel("balance : ", X, 675);
        Label balance = getLabel(Double.toString(currentAccount.getBalance()), X + 100, 675);

        Button submit = new Button("submit");
        EventHandler submitButtonAction = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!nameTextField.getText().equals(currentAccount.getFirstName())) {
                    if (nameTextField.getText().equals("")) nameError.setText("");
                    else {
                        try {
                            customerController.editFirstName(nameTextField.getText());
                            nameError.setText("");
                        } catch (Exception e) {
                            nameError.setText(e.getMessage());
                        }
                    }
                }
                if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                    confirmPasswordFieldError.setText("passwords don't match");
                }
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    confirmPasswordFieldError.setText("");
                    if (passwordField.getText().equals("")) {
                        passwordFieldError.setText("");
                    } else if (passwordField.getText().length() < 4) passwordFieldError.setText("password too short");
                    else {
                        passwordFieldError.setText("");
                        customerController.changePassword(passwordField.getText());
                    }
                }
                if (!lastNameTextField.getText().equals(currentAccount.getLastName())) {
                    if (lastNameTextField.getText().equals("")) lastNameError.setText("");
                    else {
                        try {
                            customerController.editLastName(lastNameTextField.getText());
                            lastNameError.setText("");
                        } catch (Exception e) {
                            lastNameError.setText(e.getMessage());
                        }
                    }
                }
                if (!emailTextField.getText().equals(currentAccount.getEmail())) {
                    if (emailTextField.getText().equals("")) emailError.setText("");
                    else {
                        try {
                            customerController.editEmail(emailTextField.getText());
                            emailError.setText("");
                        } catch (Exception e) {
                            emailError.setText(e.getMessage());
                        }
                    }
                }
                if (!phoneNumberTextField.getText().equals(currentAccount.getPhoneNumber())) {
                    if (phoneNumberTextField.getText().equals("")) phoneNumberError.setText("");
                    else {
                        try {
                            CustomerController.editPhoneNumber(nameTextField.getText());
                            phoneNumberError.setText("");
                        } catch (Exception e) {
                            phoneNumberError.setText(e.getMessage());
                        }
                    }
                }
            }
        };
        submit.setOnAction(submitButtonAction);

        submit.setLayoutX(360);
        submit.setLayoutY(690);
        pane.getChildren().addAll(
                usernameLabel,
                userNameError,
                username,
                passwordLabel,
                passwordFieldError,
                passwordField,
                confirmPasswordField,
                confirmPasswordFieldError,
                confirmPasswordFieldLabel,
                nameLabel,
                nameError,
                nameTextField,
                lastNameLabel,
                lastNameError,
                lastNameTextField,
                emailLabel,
                emailError,
                emailTextField,
                phoneNumberLabel,
                phoneNumberError,
                phoneNumberTextField,
                submit,
                balance,
                balanceLabel
        );
        return pane;
    }

    public Pane viewDiscountCodesPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        Label label = getLabel("discount codes", 300, 300);

        TableView tableView = getCustomerDiscountCodes();
        setPlace(tableView, 300, 320);
        pane.getChildren().addAll(label, tableView);
        return pane;
    }

    public TableView getCustomerDiscountCodes() {
        List<OffCode> offCodes = customerController.viewDiscountCodes();
        TableView<OffCode> table = new TableView<>();
        ObservableList<OffCode> data
                = FXCollections.observableArrayList(
                offCodes);

        TableColumn productName = new TableColumn("code");
        productName.setCellValueFactory(new PropertyValueFactory<>("offCode"));

        TableColumn beginDate = new TableColumn("begin date");
        beginDate.setCellValueFactory(new PropertyValueFactory<>("beginDateString"));

        TableColumn endDate = new TableColumn("end date");
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDateString"));

        table.setItems(data);
        table.getColumns().addAll(productName, beginDate, endDate);
        return table;
    }

    public Pane getOrdersPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540,800);

        TableView tv = getAllShoppingsTableView();
        setPlace(tv,300,300);

        pane.getChildren().addAll(tv);
        return pane;
    }

    public TableView getAllShoppingsTableView(){
        Customer customer =(Customer) Controller.getCurrentAccount();

        List<Order> orders = customer.getHistoryOfOrders();
        TableView<Order> table = new TableView<>();
        ObservableList<Order> data
                = FXCollections.observableArrayList(
                orders);

        TableColumn id = new TableColumn("id");
        id.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        TableColumn status = new TableColumn("shipping status");
        status.setCellValueFactory(new PropertyValueFactory<>("shippingStatusString"));

        TableColumn open = new TableColumn("open");
        open.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory
                = //
                new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Order, String> param) {
                        final TableCell<Order, String> cell = new TableCell<Order, String>() {

                            final Button button = new Button("open");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        Order  order = getTableView().getItems().get(getIndex());
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(
                                                openSingleOrderPane(order)
                                        ));
                                    });
                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        open.setCellFactory(cellFactory);

        table.setItems(data);
        table.getColumns().addAll(id,status,open);
        return table;

    }

    private Pane openSingleOrderPane(Order order) {
        Customer customer =(Customer) Controller.getCurrentAccount();

        ArrayList<ItemOfOrder> items = (ArrayList<ItemOfOrder>) order.getItems();

        TableView<ItemOfOrder> table = new TableView<>();
        ObservableList<ItemOfOrder> data
                = FXCollections.observableArrayList(
                items);

        TableColumn product = new TableColumn("product");
        product.setCellValueFactory(new PropertyValueFactory<>("productString"));

        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("priceString"));

        TableColumn discount = new TableColumn("discount");
        discount.setCellValueFactory(new PropertyValueFactory<>("discountString"));

        TableColumn date = new TableColumn("date");
        date.setCellValueFactory(new PropertyValueFactory<>("dateString"));

        TableColumn quantity = new TableColumn("dquantity");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));


        table.setItems(data);
        table.getColumns().addAll(product,price,discount,date,quantity);

        Pane pane = new Pane();
        pane.getChildren().addAll(table);
        return pane;
    }

    private TextField getTextFieldDefault(String Default, double x, double y) {
        TextField textField = new TextField();
        textField.setText(Default);
        textField.setLayoutY(y);
        textField.setLayoutX(x);
        return textField;
    }

    private Label getLabel(String text, double x, double y) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Label getLabel(String text, double x, double y, Color color) {
        Label label = new Label(text);
        label.setTextFill(color);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Label getErrorLabel(String text, double x, double y) {
        Label label = new Label(text);
        label.setTextFill(Color.RED);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Button getButton(String text, EventHandler ev) {
        Button button = new Button(text);
        button.setOnAction(ev);
        return button;
    }

    private Node setPlace(Node w, double x, double y) {
        w.setLayoutY(y);
        w.setLayoutX(x);
        return w;
    }
}
