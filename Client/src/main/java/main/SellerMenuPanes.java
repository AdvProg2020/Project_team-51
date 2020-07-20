package main;

import control.Controller;
import control.Exceptions.NotAllowedActivityException;
import control.SellerController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.*;
import model.OrderLog.SellerLog;
import model.OrderLog.ShippingStatus;
import model.People.Seller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SellerMenuPanes {

    SellerController sellerController = new SellerController(Controller.getCurrentAccount());

    public static Pane getPersonalInfoPane() {
        Seller currentAccount = (Seller) Controller.getCurrentAccount();
        SellerController sellerController = new SellerController(currentAccount);
        final int X = 300;
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);
        Label usernameLabel = getLabel("username", X, 60);
        TextField username = getTextFieldDefault(currentAccount.getUserName(), 300, 100);
        username.setEditable(false);
        Label passwordLabel = getLabel("password", X, 150);
        Label passwordFieldError = getErrorLabel("", X, 170);
        PasswordField passwordField = new PasswordField();
        setPlace(passwordField, X, 190);
        Label confirmPasswordFieldLabel = getLabel("confirm new pass", X, 240);
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
        Label brandLabel = getLabel("brand", X, 690);
        Label brandError = getErrorLabel("", X, 710);
        TextField brandTextField = getTextFieldDefault(currentAccount.getBrandName(), X, 730);
        Button submit = new Button("submit");
        EventHandler submitButtonAction = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!nameTextField.getText().equals(currentAccount.getFirstName())) {
                    if (nameTextField.getText().equals("")) nameError.setText("");
                    else {
                        try {
                            SellerController.editFirstName(nameTextField.getText());
                            nameError.setText("");
                        } catch (Exception e) {
                            nameError.setText(e.getMessage());
                        }
                    }
                } else {
                    nameError.setText("");
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
                        sellerController.changePassword(passwordField.getText());
                    }
                }
                if (!lastNameTextField.getText().equals(currentAccount.getLastName())) {
                    if (lastNameTextField.getText().equals("")) lastNameError.setText("");
                    else {
                        try {
                            SellerController.editLastName(lastNameTextField.getText());
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
                            SellerController.editEmail(emailTextField.getText());
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
                            SellerController.editPhoneNumber(nameTextField.getText());
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
                brandLabel,
                brandError,
                brandTextField,
                submit
        );
        return pane;
    }//v1

    private static TextField getTextFieldDefault(String Default, double x, double y) {
        TextField textField = new TextField();
        textField.setText(Default);
        textField.setLayoutY(y);
        textField.setLayoutX(x);
        return textField;
    }

    private static Label getLabel(String text, double x, double y) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private static Label getLabel(String text, double x, double y, Color color) {
        Label label = new Label(text);
        label.setTextFill(color);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private static Label getErrorLabel(String text, double x, double y) {
        Label label = new Label(text);
        label.setTextFill(Color.RED);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private static Button getButton(String text, EventHandler ev) {
        Button button = new Button(text);
        button.setOnAction(ev);
        return button;
    }

    private static Node setPlace(Node w, double x, double y) {
        w.setLayoutY(y);
        w.setLayoutX(x);
        return w;
    }

    public Pane getCreateAuctionPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        final int X = 300;
        ArrayList<Product> selected = new ArrayList<>();
        Label beginDateLabel = getLabel("begin date", X, 390);
        Label beginDateError = getErrorLabel("", X, 410);
        DatePicker beginDatePicker = new DatePicker();
        setPlace(beginDatePicker, X, 430);

        Label endDateLabel = getLabel("end date", X, 480);
        Label endDateError = getErrorLabel("", X, 500);
        DatePicker endDatePicker = new DatePicker();
        setPlace(endDatePicker, X, 520);

        Label percentLabel = getLabel("percent", X, 570);
        Slider percentSlider = new Slider(1, 99, 1);
        Label percentSliderAmount = new Label("");
        setPlace(percentSliderAmount, X + 120, 590);
        setPlace(percentSlider, X, 610);
        percentSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                percentSliderAmount.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        });

        Label selectedLabel = getLabel("selected products", 600, 300);
        Label selectedError = getErrorLabel("", 600, 320);

        TableView products = getProductsTableViewAuction(selected); //todo check
        setPlace(products, 600, 340);

        Button confirm = getButton("confirm", event -> {
            if (beginDatePicker.getValue() == null) {
                beginDateError.setText("please select a date");
            } else {
                beginDateError.setText("");
            }
            if (endDatePicker.getValue() == null) {
                endDateError.setText("please select a date");
            } else endDateError.setText("");
            if (endDatePicker.getValue() != null && beginDatePicker.getValue() != null) {
                if (endDatePicker.getValue().isBefore(beginDatePicker.getValue())) {
                    endDateError.setText("end date must be after start");
                } else {
                    endDateError.setText("");
                    beginDateError.setText("");
                }
            }

            if (selected.size() < 1) {
                selectedError.setText("select at least one product");
            } else {
                selectedError.setText("");
            }

            if (beginDateError.getText().equals("") &&
                    endDateError.getText().equals("") &&
                    selectedError.getText().equals("")) {
                new Auction((Seller) Controller.getCurrentAccount(),
                        toDate(beginDatePicker.getValue()),
                        toDate(endDatePicker.getValue()),
                        selected,
                        (int) percentSlider.getValue());
            }
        });
        pane.getChildren().addAll(
                beginDateLabel,
                beginDateError,
                beginDatePicker,
                endDateLabel,
                endDateError,
                endDatePicker,
                percentLabel,
                percentSlider,
                percentSliderAmount,
                selectedLabel,
                selectedError,
                products,
                confirm
        );

        return pane;
    }//v1

//    public Pane getAttributesPane(Category category){
//        Pane pane = new Pane();
//
//        ArrayList<Attributes> attributes = new ArrayList<>();
//        TableView tv = getAttributesTableView (category,attributes);
//        setPlace(tv,0,0);
//        Button back , confirm;
//
//        back = getButton("back" , event -> {
//            // TOD: ۲۶/۰۶/۲۰۲۰ go back
//        });
//        setPlace(back,100,600);
//        confirm = getButton("confirm" , event -> {
//
//        });
//        setPlace(confirm,200,600);
//        pane.getChildren().addAll(tv,back,confirm);
//        return pane;
//    }//not done

    public TableView getProductsTableViewAuction(List<Product> selection) {
        List<Product> products = sellerController.getProductsForAuction();
        TableView<Product> table = new TableView<>();
        ObservableList<Product> data
                = FXCollections.observableArrayList(
                products);

        TableColumn productName = new TableColumn("name");
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn select = new TableColumn("select");
        select.setCellValueFactory(new PropertyValueFactory<>("uselessString"));

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory
                = //
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Product, String> param) {
                        final TableCell<Product, String> cell = new TableCell<Product, String>() {

                            final CheckBox checkBox = new CheckBox();
                            boolean firstTime = true;

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Product product = getTableView().getItems().get(getIndex());
                                    if (firstTime) {
                                        if (selection.contains(product)) checkBox.setSelected(true);
                                        firstTime = false;
                                    }
                                    checkBox.setOnAction(event -> {
                                        if (checkBox.isSelected()) selection.add(product);
                                        else selection.remove(product);
                                    });
                                    setGraphic(checkBox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        select.setCellFactory(cellFactory);

        table.setItems(data);
        table.getColumns().addAll(productName, select);
        return table;
    }//v1

    public Pane getSalesHistoryPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        TableView<SellerLog> sellerLogsTable = new TableView<>();
        ObservableList<SellerLog> data
                = FXCollections.observableArrayList(
                sellerController.getSellerLogs());

        TableColumn id = new TableColumn("id");
        id.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn name = new TableColumn("buyer");
        name.setCellValueFactory(new PropertyValueFactory<>("buyerName"));

        TableColumn open = new TableColumn("open");
        name.setCellValueFactory(new PropertyValueFactory<>("buyerName"));

        Callback<TableColumn<SellerLog, String>, TableCell<SellerLog, String>> cellFactory
                = //
                new Callback<TableColumn<SellerLog, String>, TableCell<SellerLog, String>>() {
                    @Override
                    public TableCell call(final TableColumn<SellerLog, String> param) {
                        final TableCell<SellerLog, String> cell = new TableCell<SellerLog, String>() {

                            final Button button = new Button("open");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        SellerLog sellerLog = getTableView().getItems().get(getIndex());
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(getSingleSaleHistoryPane(sellerLog)));
                                    });
                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        sellerLogsTable.setItems(data);
        open.setCellFactory(cellFactory);
        sellerLogsTable.getColumns().addAll(id, name, open);

        setPlace(sellerLogsTable, 300, 300);

        pane.getChildren().addAll(sellerLogsTable);

        return pane;
    } //v1

    public Pane getSingleSaleHistoryPane(SellerLog sellerLog) {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        Label label = getLabel("buyer", 300, 300);
        Label nameLabel = getLabel(sellerLog.getBuyerName(), 300, 320);

        ShippingStatus shippingStatus = sellerLog.getStatus();
        String status;
        if (shippingStatus.equals(ShippingStatus.RECEIVED)) status = "received";
        else if (shippingStatus.equals(ShippingStatus.RETURNED)) status = "returned";
        else status = "sent";

        Label statusLabel = getLabel("shipping status", 300, 370);
        Label statusValue = getLabel(status, 300, 390);

        TableView<SellerLog> tableView = new TableView<>();

        ObservableList<ItemOfOrder> data
                = FXCollections.observableArrayList(
                sellerLog.getItems()
        );

        TableColumn product = new TableColumn("product");
        product.setCellValueFactory(new PropertyValueFactory<>("productString"));

        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("priceString"));

        TableColumn discount = new TableColumn("discount");
        discount.setCellValueFactory(new PropertyValueFactory<>("discountString"));

        TableColumn date = new TableColumn("date");
        date.setCellValueFactory(new PropertyValueFactory<>("dateString"));

        TableColumn quantity = new TableColumn("quantity");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));

        tableView.getColumns().addAll(product, price, discount, date, quantity);
        pane.getChildren().addAll(label, nameLabel, statusLabel, statusValue, tableView);
        return pane;
    } //v1

    public Pane getAllAuctionsPane() {
        TableView tableView = new TableView();
        ObservableList<Auction> data
                = FXCollections.observableArrayList(
                sellerController.getSellerAuctions());

        TableColumn auctionId = new TableColumn("id");
        auctionId.setCellValueFactory(new PropertyValueFactory<>("auctionId"));

        TableColumn open = new TableColumn("open");
        open.setCellValueFactory(new PropertyValueFactory<>("auctionId"));

        Callback<TableColumn<Auction, String>, TableCell<Auction, String>> cellFactory
                = //
                new Callback<TableColumn<Auction, String>, TableCell<Auction, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Auction, String> param) {
                        final TableCell<Auction, String> cell = new TableCell<Auction, String>() {

                            final Button b = new Button("edit");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    b.setOnKeyTyped(event -> {
                                        Auction auction = getTableView().getItems().get(getIndex());

                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(getEditAuctionPane(auction)));
                                        stage.show();

                                    });
                                    setGraphic(b);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        tableView.setItems(data);
        open.setCellFactory(cellFactory);
        tableView.getColumns().addAll(auctionId, open);

        Pane pane = new Pane();
        pane.getChildren().addAll(tableView);
        return pane;
    } //v1

    public Pane getEditAuctionPane(Auction auction) {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        final int X = 300;
        ArrayList<Product> selected = new ArrayList<>();

        Label nameLabel = getLabel("name", X, 300);
        TextField nameField = getTextFieldDefault("", X, 340);
        nameField.setText(auction.getAuctionId());
        nameField.setEditable(false);

        Label beginDateLabel = getLabel("begin date", X, 390);
        Label beginDateError = getErrorLabel("", X, 410);
        DatePicker beginDatePicker = new DatePicker();
        setPlace(beginDatePicker, X, 430);
        beginDatePicker.setValue(toLocalDate(auction.getBeginDate()));

        Label endDateLabel = getLabel("end date", X, 480);
        Label endDateError = getErrorLabel("", X, 500);
        DatePicker endDatePicker = new DatePicker();
        setPlace(endDatePicker, X, 520);
        endDatePicker.setValue(toLocalDate(auction.getEndDate()));

        Label percentLabel = getLabel("percent", X, 570);
        Slider percentSlider = new Slider(1, 99, 1);
        Label percentSliderAmount = new Label("");
        setPlace(percentSliderAmount, X + 120, 590);
        setPlace(percentSlider, X, 610);
        percentSliderAmount.setText(Integer.toString(auction.getOffPercentage()));
        percentSlider.setValue(auction.getOffPercentage());
        percentSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> percentSliderAmount.textProperty().setValue(
                String.valueOf(newValue.intValue())));

        Label selectedLabel = getLabel("selected products", 500, 300);
        Label selectedError = getErrorLabel("", 500, 320);

        TableView products = getProductsTableViewAuction(selected);
        setPlace(products, 500, 340);

        Button confirm = getButton("confirm", event -> {

            if (beginDatePicker.getValue().equals(null)) {
                beginDateError.setText("please select a date");
            } else beginDateError.setText("");
            if (endDatePicker.getValue().equals(null)) {
                endDateError.setText("please select a date");
            } else endDateError.setText("");
            if (endDatePicker.getValue() != null && beginDatePicker.getValue() != null) {
                if (endDatePicker.getValue().isAfter(beginDatePicker.getValue())) {
                    endDateError.setText("end date must be after start");
                } else {
                    endDateError.setText("");
                    beginDateError.setText("");
                }
            }

            if (selected.size() < 1) {
                selectedError.setText("select at least one product");
            } else {
                selectedError.setText("");
            }

            if (beginDateError.getText().equals("") &&
                    endDateError.getText().equals("") &&
                    selectedError.getText().equals("")) {
                sellerController.editAuction(auction,
                        toDate(beginDatePicker.getValue()), toDate(endDatePicker.getValue()), selected, (int) percentSlider.getValue());
            }
        });
        pane.getChildren().addAll(
                nameLabel,
                nameField,
                beginDateLabel,
                beginDateError,
                beginDatePicker,
                endDateLabel,
                endDateError,
                endDatePicker,
                percentLabel,
                percentSlider,
                percentSliderAmount,
                selectedLabel,
                selectedError,
                products,
                confirm
        );
        return pane;

    }//v1

    public Pane getCreateProductPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);
        ArrayList<Attributes> attributes = new ArrayList<>();
        HashMap<Attributes, String> attributesStringMap = new HashMap<>();

        final int X = 300;
        Label nameLabel = getLabel("name", X, 300);
        Label nameError = getErrorLabel("", X, 320);
        TextField nameField = getTextFieldDefault("", X, 340);

        Label brandLabel = getLabel("brand", X, 390);
        Label brandError = getErrorLabel("", X, 410);
        TextField brandField = getTextFieldDefault("", X, 430);

        Label quantityLabel = getLabel("amount", X, 480);
        Slider quantitySlider = new Slider(0, 99, 1);
        Label quantityAmount = new Label("");
        setPlace(quantityAmount, X + 120, 480);
        setPlace(quantitySlider, X, 500);

        Label priceLabel = getLabel("price", X, 550);
        Label priceError = getErrorLabel("", X, 570);
        TextField priceField = getTextFieldDefault("", X, 590);
        priceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    priceField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label categoryLabel = getLabel("category", X, 630);
        Label categoryError = getErrorLabel("", X, 650);
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(SellerController.getAllCategories());
        setPlace(categoryComboBox, X, 670);

        Label descriptionLabel = getLabel("description", X, 710);
        TextField descrioptionField = getTextFieldDefault("", X + 120, 710);

        Label attributeError = getErrorLabel("", 300, 740);

        AtomicBoolean enteredAttributes = new AtomicBoolean(false);
        Button attributesButton = getButton("attributes", event -> {
            if (categoryComboBox.getValue() == null) attributeError.setText("please select category");
            else {
                enteredAttributes.set(true);
                categoryError.setText("");
                Stage stage = new Stage();
                stage.setScene(new Scene(getAttributesTableViewCreateProduct(
                        categoryComboBox.getValue(), attributesStringMap
                )));
                stage.show();
            }
        });

        setPlace(attributesButton, 400, 755);

        Button done = getButton("done", event -> {
            if (nameField.getText().equals("")) nameError.setText("please select a name");
            else if (SellerController.isProductNameTaken(nameField.getText())) {
                nameError.setText("this name is taken");
            } else {
                nameError.setText("");
            }

            if (brandField.getText().equals("")) {
                brandError.setText("enter a brand");
            } else {
                brandError.setText("");
            }

            if (priceField.getText().equals("") || Integer.parseInt(priceField.getText()) == 0) {
                priceError.setText("please enter price");
            } else {
                priceError.setText("");
            }

            if (categoryComboBox.getValue().equals(null)) {
                categoryError.setText("please select category");
            } else {
                categoryError.setText("");
            }

            if (enteredAttributes.get() == true) {
                attributeError.setText("");
            } else attributeError.setText("please fill attributes");

            if (nameError.getText().equals("") &&
                    brandError.getText().equals("") &&
                    priceError.getText().equals("") &&
                    categoryError.getText().equals("") &&
                    attributeError.getText().equals("")) {
                try {
                    System.out.println("created product 1");
                    sellerController.addProduct(new Product("", nameField.getText(), brandField.getText(), Double.parseDouble(priceField.getText()),
                            (Seller) Controller.getCurrentAccount(), (int) quantitySlider.getValue(), categoryComboBox.getValue(),
                            descrioptionField.getText(), attributesStringMap));
                } catch (NotAllowedActivityException e) {
                    attributeError.setText(e.getMessage());
                }
            }
        });

        setPlace(done, 350, 755);

        pane.getChildren().addAll(nameError, nameField, nameLabel,
                quantityLabel, quantityAmount, quantitySlider,
                categoryLabel, categoryComboBox, categoryError,
                brandError, brandField, brandLabel,
                priceError, priceField, priceLabel,
                descrioptionField, descriptionLabel,
                attributesButton, attributeError, done
        );
        return pane;
    }//v1

    public TableView getAttributesTableViewCreateProduct(Category c, HashMap<Attributes, String> hashMap) {
        TableView<Attributes> attributesTableView = new TableView<>();
        ObservableList<Attributes> data
                = FXCollections.observableArrayList(
                c.getAttributes());

        TableColumn categoryName = new TableColumn("id");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("attributeId"));

        TableColumn select = new TableColumn("field");
        select.setCellValueFactory(new PropertyValueFactory<>("field"));

        Callback<TableColumn<Attributes, String>, TableCell<Attributes, String>> cellFactory
                = //
                new Callback<TableColumn<Attributes, String>, TableCell<Attributes, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Attributes, String> param) {
                        final TableCell<Attributes, String> cell = new TableCell<Attributes, String>() {

                            final TextField tf = new TextField();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    tf.setOnKeyTyped(event -> {
                                        Attributes at = getTableView().getItems().get(getIndex());
                                        hashMap.put(at, tf.getText());
                                    });
                                    setGraphic(tf);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        attributesTableView.setItems(data);
        select.setCellFactory(cellFactory);
        attributesTableView.getColumns().addAll(categoryName, select);
        return attributesTableView;
    }//v1

    public Pane getManageProductsPane() {
        System.out.println(sellerController.getSellerProducts().size());
        System.out.println(sellerController.getSellerProducts());
        Pane pane = new Pane();

        TableView tv = getManageProductsTableView();

        pane.getChildren().addAll(tv);
        return pane;
    }//v1

    public TableView getManageProductsTableView() {
        TableView<Product> tableView = new TableView<>();
        ObservableList<Product> data
                = FXCollections.observableList(
                sellerController.getSellerProducts());

        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("priceString"));

        TableColumn categoryName = new TableColumn("category");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryString"));

        TableColumn edit = new TableColumn("edit");
        edit.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn delete = new TableColumn("delete");
        delete.setCellValueFactory(new PropertyValueFactory<>("name"));

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactoryEdit
                = //
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Product, String> param) {
                        final TableCell<Product, String> cell = new TableCell<Product, String>() {

                            final Button b = new Button("edit");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    b.setOnAction(actionEvent -> {
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(
                                                getEditProductPane(getTableView().getItems().get(getIndex()))));
                                        stage.show();
                                    });
                                    setGraphic(b);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        edit.setCellFactory(cellFactoryEdit);

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Product, String> param) {
                        final TableCell<Product, String> cell = new TableCell<Product, String>() {

                            final Button b = new Button("delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Product p = getTableView().getItems().get(getIndex());
                                    b.setOnAction(actionEvent -> {
                                        sellerController.removeProduct(p);
                                        tableView.getItems().remove(p);
                                    });
                                    setGraphic(b);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        delete.setCellFactory(cellFactoryDelete);

        tableView.setItems(data);
        tableView.getColumns().addAll(name,
                price,
                edit,
                delete);
        return tableView;
    }//v1

    private Pane getEditProductPane(Product product) {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);
        HashMap<Attributes, String> attributesStringMap = new HashMap<>();

        final int X = 300;
        Label nameLabel = getLabel("name", X, 300);
        Label nameError = getErrorLabel("", X, 320);
        TextField nameField = getTextFieldDefault(product.getName(), X, 340);

        Label brandLabel = getLabel("brand", X, 390);
        Label brandError = getErrorLabel("", X, 410);
        TextField brandField = getTextFieldDefault(product.getBrandName(), X, 430);

        Label quantityLabel = getLabel("amount", X, 480);
        Slider quantitySlider = new Slider(0, 99, 1);
        quantitySlider.setValue(product.getQuantityForSeller((Seller) Controller.getCurrentAccount()));
        Label quantityAmount = new Label("");
        quantityAmount.setText(Integer.toString(product.getQuantityForSeller((Seller) Controller.getCurrentAccount())));
        setPlace(quantityAmount, X + 120, 480);
        setPlace(quantitySlider, X, 500);

        Label priceLabel = getLabel("price", X, 550);
        Label priceError = getErrorLabel("", X, 570);
        TextField priceField = getTextFieldDefault("", X, 590);
        priceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    priceField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label categoryLabel = getLabel("category", X, 630);
        Label categoryError = getErrorLabel("", X, 650);
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(SellerController.getAllCategories());
        categoryComboBox.getSelectionModel().select(product.getParentCategory());
        setPlace(categoryComboBox, X, 670);

        Label descriptionLabel = getLabel("description", X, 710);
        TextField descrioptionField = getTextFieldDefault(product.getDescription(), X + 120, 710);
        Label attributeError = getErrorLabel("", 300, 740);

        Button attributesButton = getButton("attributes", event -> {
            if (categoryComboBox.getValue() == null) attributeError.setText("please select category");
            else {
                categoryError.setText("");
                Stage stage = new Stage();
                stage.setScene(new Scene(getAttributesTableViewCreateProduct(
                        categoryComboBox.getValue(), attributesStringMap
                )));
            }
        });

        setPlace(attributesButton, 400, 755);

        Button done = getButton("done", event -> {
            if (nameField.getText().equals(product.getName())) ;
            if (SellerController.isProductNameTaken(nameField.getText())) {
                nameError.setText("this name is taken");
            } else {
                SellerController.editProduct(product, "name", nameField.getText());
                nameError.setText("");
            }

            if (brandField.getText().equals("")) {
                brandError.setText("enter a brand");
            } else {
                brandError.setText("");
            }

            if (priceField.getText().equals("") || Integer.parseInt(priceField.getText()) == 0) {
                priceError.setText("please enter price");
            } else {
                priceError.setText("");
            }

            if (categoryComboBox.getValue().equals(null)) {
                categoryError.setText("please select category");
            } else {
                categoryError.setText("");
            }

            if (nameError.equals("") &&
                    brandError.equals("") &&
                    priceError.equals("") &&
                    categoryError.equals("") &&
                    attributeError.equals("")) {
                try {
                    sellerController.addProduct(new Product("", nameField.getText(), brandField.getText(), Double.parseDouble(priceField.getText()),
                            (Seller) Controller.getCurrentAccount(), (int) quantitySlider.getValue(), categoryComboBox.getValue(),
                            descrioptionField.getText(), attributesStringMap));
                } catch (NotAllowedActivityException e) {
                    attributeError.setText(e.getMessage());
                }
            }
        });

        setPlace(done, 350, 755);

        pane.getChildren().addAll(nameError, nameField, nameLabel, quantityLabel,
                categoryLabel, categoryComboBox, categoryError,
                brandError, brandField, brandLabel,
                priceError, priceField, priceLabel,
                descrioptionField, descriptionLabel,
                done
        );
        return pane;
    }// v1 todo

    public Pane getViewCategoriesPane() {
        TableView<Category> tableView = new TableView<>();

        ObservableList<Category> data =
                FXCollections.observableList(sellerController.listCategories());
        tableView.setItems(data);

        TableColumn category = new TableColumn("category name");
        category.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn open = new TableColumn("open");
        open.setCellValueFactory(new PropertyValueFactory<>("name"));

        Callback<TableColumn<Category, String>, TableCell<Category, String>> cellFactoryOpen
                = //
                new Callback<TableColumn<Category, String>, TableCell<Category, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Category, String> param) {
                        final TableCell<Category, String> cell = new TableCell<Category, String>() {

                            final Button b = new Button("open");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    b.setOnAction(actionEvent -> {
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(
                                                getViewCategoryPane(getTableView().getItems().get(getIndex()))));
                                        stage.show();
                                    });
                                    setGraphic(b);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        open.setCellFactory(cellFactoryOpen);

        tableView.getColumns().addAll(category, open);
        return new Pane(tableView);
    }//v1

    private Pane getViewCategoryPane(Category category) {

        final int X = 300;
        Label father = getLabel("father", X, 300);
        Label fatherLabel = getLabel(category.getParentCategory().getName(), X, 320);


        ArrayList<String> subNames = new ArrayList<>();
        for (Category c : category.getSubCategories().values()) {
            subNames.add(c.getName());
        }

        TableView<String> subCategoriesTV = new TableView(FXCollections.observableArrayList(
                new ArrayList<String>(subNames)));
        TableColumn<String, String> tc = new TableColumn<>("sub categories");
        tc.setCellValueFactory((p) -> {
            return new ReadOnlyStringWrapper(p.getValue());
        });
        subCategoriesTV.getColumns().add(tc);

        ArrayList<String> attributeNames = new ArrayList<>();
        for (Attributes a : category.getAttributes()) {
            attributeNames.add(a.getField());
        }

        TableView<String> attributesTV = new TableView(FXCollections.observableArrayList(
                new ArrayList<String>(attributeNames)));
        TableColumn<String, String> tc2 = new TableColumn<>("attributes");
        tc.setCellValueFactory((p) -> {
            return new ReadOnlyStringWrapper(p.getValue());
        });
        subCategoriesTV.getColumns().add(tc2);

        ArrayList<String> productNames = new ArrayList<>();
        for (Product p : category.getCategoryProducts()) {
            attributeNames.add(p.getName());
        }

        TableView<String> productsTV = new TableView(FXCollections.observableArrayList(
                new ArrayList<String>(productNames)));
        TableColumn<String, String> tc3 = new TableColumn<>("products");
        tc.setCellValueFactory((p) -> {
            return new ReadOnlyStringWrapper(p.getValue());
        });
        subCategoriesTV.getColumns().add(tc3);

        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        setPlace(subCategoriesTV, 500, 300);
        setPlace(attributesTV, 800, 300);
        setPlace(productsTV, 1100, 300);
        pane.getChildren().addAll(father, fatherLabel,
                subCategoriesTV, attributesTV, productsTV);

        return pane;
    }//v1

    public TableView getAttributesTableView(Category category) {
        return null;
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}