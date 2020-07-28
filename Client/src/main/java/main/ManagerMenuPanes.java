package main;

import control.Controller;
import control.Exceptions.InvalidOffCodeException;
import control.ManagerController;
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
import model.Attributes;
import model.Category;
import model.OffCode;
import model.People.Account;
import model.People.Customer;
import model.Product;
import model.Requests.Request;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


public class ManagerMenuPanes {
    ManagerController managerController = new ManagerController(Controller.getCurrentAccount());
    Account currentAccount = Controller.getCurrentAccount();

    public Pane getPersonalInfoPane() {
        final int X = 60;
        Pane pane = new Pane();
        pane.setPrefSize(300, 800);
        Label usernameLabel = getLabel("username", X, 80);
        TextField username = getTextFieldDefault(currentAccount.getUserName(), X, 100);
        username.setEditable(false); // todo bug
        Label passwordLabel = getLabel("password", X, 160);
        Label passwordFieldError = getErrorLabel("", X, 180);
        PasswordField passwordField = new PasswordField();
        setPlace(passwordField, X, 200);
        Label confirmPasswordFieldLabel = getLabel("confirm new pass", X, 250);
        Label confirmPasswordFieldError = getErrorLabel("", X, 270);
        PasswordField confirmPasswordField = new PasswordField();
        setPlace(confirmPasswordField, X, 290);
        Label nameLabel = getLabel("name", X, 340);
        Label nameError = getErrorLabel("", X, 360);
        TextField nameTextField = getTextFieldDefault(currentAccount.getFirstName(), X, 380);
        Label lastNameLabel = getLabel("last name", X, 430);
        Label lastNameError = getErrorLabel("", X, 450);
        TextField lastNameTextField = getTextFieldDefault(currentAccount.getLastName(), X, 470);
        Label emailLabel = getLabel("email", X, 520);
        Label emailError = getErrorLabel("", X, 520);
        TextField emailTextField = getTextFieldDefault(currentAccount.getEmail(), X, 560);
        Label phoneNumberLabel = getLabel("phone number", X, 610);
        Label phoneNumberError = getErrorLabel("", X, 630);
        TextField phoneNumberTextField = getTextFieldDefault(currentAccount.getPhoneNumber(), X, 650);
        Button submit = new Button("submit");
        EventHandler submitButtonAction = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!nameTextField.getText().equals(currentAccount.getFirstName())) {
                    if (nameTextField.getText().equals("")) nameError.setText("");
                    else {
                        try {
                            managerController.editFirstName(nameTextField.getText());
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
                        managerController.changePassword(passwordField.getText());
                    }
                }
                if (!lastNameTextField.getText().equals(currentAccount.getLastName())) {
                    if (lastNameTextField.getText().equals("")) lastNameError.setText("");
                    else {
                        try {
                            managerController.editLastName(lastNameTextField.getText());
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
                            managerController.editEmail(emailTextField.getText());
                            emailError.setText("");
                        } catch (Exception e) {
                            emailError.setText(e.getMessage());
                        }
                    }
                } else {
                    emailError.setText("");
                }
                if (!phoneNumberTextField.getText().equals(currentAccount.getPhoneNumber())) {
                    if (phoneNumberTextField.getText().equals("")) phoneNumberError.setText("");
                    else {
                        try {
                            managerController.editPhoneNumber(phoneNumberTextField.getText());
                            phoneNumberError.setText("");
                        } catch (Exception e) {
                            phoneNumberError.setText(e.getMessage());
                        }
                    }
                } else {
                    phoneNumberError.setText("");
                }
            }
        };
        submit.setOnAction(submitButtonAction);
        submit.setLayoutX(80);
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
                submit
        );
        return pane;
    }//v1

    public Pane getManageRequestsPane() {
        Pane pane = new Pane();
        TableView tv = getRequestsTebleView();
        pane.getChildren().addAll(tv);
        return pane;
    }//v1

    public TableView getManageUsersTableView() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Manager", "Customer", "Seller");
        List<Account> accounts = managerController.getAllProfiles();
        TableView<Account> table = new TableView<>();
        ObservableList<Account> data
                = FXCollections.observableArrayList(
                accounts
        );

        TableColumn username = new TableColumn("username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn firstName = new TableColumn("first name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn lastName = new TableColumn("last name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn balance = new TableColumn("balance");
        balance.setCellValueFactory(new PropertyValueFactory<>("balanceString"));

        TableColumn type = new TableColumn("type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn delete = new TableColumn("delete");
        delete.setCellValueFactory(new PropertyValueFactory<>("userName"));


        Callback<TableColumn<Account, String>, TableCell<Account, String>> cellFactoryDeleteAccount
                = //
                new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Account, String> param) {
                        final TableCell<Account, String> cell = new TableCell<Account, String>() {

                            final Button btn = new Button("delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(ev -> {
                                        Account account = getTableView().getItems().get(getIndex());
                                        table.getItems().remove(account);
                                        try {
                                            managerController.deleteUser(account.getUserName());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<Account, String>, TableCell<Account, String>> cellFactorysecChangeType
                = //
                new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Account, String> param) {
                        final TableCell<Account, String> cell = new TableCell<Account, String>() {

                            final ComboBox comboBox = getAccountTypeCombobox();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) comboBox.getSelectionModel().select(item);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    comboBox.setOnAction(event -> {
                                        Account account = getTableView().getItems().get(getIndex());
                                        if (!comboBox.getValue().equals(account.getType())) {
                                            managerController.setAccountType(account, (String) comboBox.getValue());
                                            System.out.println("combo" + Account.getAllAccounts());
                                        }
                                    });
                                    setGraphic(comboBox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        delete.setCellFactory(cellFactoryDeleteAccount);
        type.setCellFactory(cellFactorysecChangeType);

        table.setItems(data);
        table.getColumns().addAll(username, firstName, lastName, balance, delete, type);
        return table;
    }//v1

    private ComboBox getAccountTypeCombobox() {
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll("Manager", "Customer", "Seller");
        return comboBox;
    }//v1

    public TableView getRequestsTebleView() {
        TableView<Request> table = new TableView<>();
        ObservableList<Request> data
                = FXCollections.observableArrayList(
                managerController.getAllRequests());

        TableColumn requestDetail = new TableColumn("Detail");
        requestDetail.setCellValueFactory(new PropertyValueFactory<>("detail"));

        TableColumn accept = new TableColumn("accept");
        accept.setCellValueFactory(new PropertyValueFactory<>("detail"));

        TableColumn decline = new TableColumn("decline");
        decline.setCellValueFactory(new PropertyValueFactory<>("detail"));


        Callback<TableColumn<Request, String>, TableCell<Request, String>> cellFactory
                = //
                new Callback<TableColumn<Request, String>, TableCell<Request, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Request, String> param) {
                        final TableCell<Request, String> cell = new TableCell<Request, String>() {

                            final Button btn = new Button("accept");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Request request = getTableView().getItems().get(getIndex());
                                        try {
                                            table.getItems().remove(request);
                                            request.accept();
                                        } catch (Exception e) {
                                            if (e instanceof ParseException) System.err.println("parse exception");
                                            else System.err.println(e.getMessage());
                                        }
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<Request, String>, TableCell<Request, String>> cellFactorysec
                = //
                new Callback<TableColumn<Request, String>, TableCell<Request, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Request, String> param) {
                        final TableCell<Request, String> cell = new TableCell<Request, String>() {

                            final Button btn = new Button("decline");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Request request = getTableView().getItems().get(getIndex());
                                        table.getItems().remove(request);
                                        request.delete();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        accept.setCellFactory(cellFactory);
        decline.setCellFactory(cellFactorysec);

        table.setItems(data);
        table.getColumns().addAll(requestDetail, accept, decline);
        return table;
    }//v1

    public TableView getManageProductsTableView() {

        TableView<Product> table = new TableView<>();
        ObservableList<Product> data
                = FXCollections.observableArrayList(
                Product.getAllProducts());

        TableColumn productName = new TableColumn("name");
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn delete = new TableColumn("delete");
        delete.setCellValueFactory(new PropertyValueFactory<>("uselessString"));


        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory
                = //
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Product, String> param) {
                        final TableCell<Product, String> cell = new TableCell<Product, String>() {

                            final Button btn = new Button("delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Product product = getTableView().getItems().get(getIndex());
                                        try {
                                            managerController.removeProduct(product.getProductId());
                                            table.getItems().remove(product);
                                        } catch (Exception e) {
                                            System.err.println(e.getMessage());
                                        }
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        delete.setCellFactory(cellFactory);

        table.setItems(data);
        table.getColumns().addAll(productName, delete);
        return table;
    }//v1

    // note that this arraylist can be empty but will get filled by the table
    public TableView getProductsTableViewForOffCode(ArrayList<Product> selection) {
        TableView<Product> table = new TableView<>();
        ObservableList<Product> data
                = FXCollections.observableArrayList(
                managerController.getAllProducts());

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

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    checkBox.setOnAction(event -> {
                                        Product product = getTableView().getItems().get(getIndex());
                                        if (checkBox.isSelected()) selection.add(product);
                                        else selection.remove(product);
                                        System.out.println(selection);
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

    public TableView getPeopleTableViewForOffCode(List<Customer> selection) {
        TableView<Account> table = new TableView<>();
        ObservableList<Account> data
                = FXCollections.observableArrayList(
                managerController.getAllCustomers());

        TableColumn productName = new TableColumn("name");
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn select = new TableColumn("select");
        select.setCellValueFactory(new PropertyValueFactory<>("uselessString"));


        Callback<TableColumn<Account, String>, TableCell<Account, String>> cellFactory
                = //
                new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
                    @Override
                    public TableCell<Account, String> call(TableColumn<Account, String> accountStringTableColumn) {
                        final TableCell<Account, String> cell = new TableCell<Account, String>() {

                            final CheckBox checkBox = new CheckBox();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    checkBox.setOnAction(event -> {
                                        Account account = getTableView().getItems().get(getIndex());
                                        if (checkBox.isSelected()) selection.add((Customer) account);
                                        else selection.remove(account);
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

    public TableView<OffCode> getAllOffCodesTableView() {
        List<OffCode> offCodes = managerController.getAllOffcodes();
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

        TableColumn status = new TableColumn("status");
        status.setCellValueFactory(new PropertyValueFactory<>("statusString"));

        TableColumn open = new TableColumn("open");
        open.setCellValueFactory(new PropertyValueFactory<>("uselessString"));

        TableColumn delete = new TableColumn("delete");
        delete.setCellValueFactory(new PropertyValueFactory<>("uselessString"));


        Callback<TableColumn<OffCode, String>, TableCell<OffCode, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<OffCode, String>, TableCell<OffCode, String>>() {
                    @Override
                    public TableCell call(final TableColumn<OffCode, String> param) {
                        final TableCell<OffCode, String> cell = new TableCell<OffCode, String>() {

                            final Button delete = new Button("delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    delete.setOnAction(event -> {
                                        OffCode offCode = getTableView().getItems().get(getIndex());
                                        try {
                                            managerController.removeDiscountCode(offCode.getOffCode());
                                            table.getItems().remove(offCode);
                                        } catch (InvalidOffCodeException e) {
                                            e.printStackTrace();
                                        }
                                        // after deleting the console parts of code this can be changed
                                        // to only get the offCode not the name, the exception will vanish too
                                    });
                                    setGraphic(delete);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        delete.setCellFactory(cellFactoryDelete);


        Callback<TableColumn<OffCode, String>, TableCell<OffCode, String>> cellFactoryOpen
                = //
                new Callback<TableColumn<OffCode, String>, TableCell<OffCode, String>>() {
                    @Override
                    public TableCell call(final TableColumn<OffCode, String> param) {
                        final TableCell<OffCode, String> cell = new TableCell<OffCode, String>() {

                            final Button open = new Button("open");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    open.setOnAction(event -> {
                                        OffCode offCode = getTableView().getItems().get(getIndex());
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(getEditDiscountCodePane(offCode)));
                                        stage.show();
                                    });
                                    setGraphic(open);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        open.setCellFactory(cellFactoryOpen);


        table.setItems(data);
        table.getColumns().addAll(productName, beginDate, endDate, status, delete, open);
        return table;
    }//v1

    public Pane getViewOffcodesPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        Label label = getLabel("offCodes", 300, 300);
        TableView tableView = getAllOffCodesTableView();
        setPlace(tableView, 300, 320);

        pane.getChildren().addAll(label, tableView);
        return pane;
    }//v1

    public Pane getEditDiscountCodePane(OffCode offCode) {
        Pane pane = new Pane();
        List<Account> selectedAccounts;
        selectedAccounts = offCode.getAppliedAccounts();
        pane.setPrefSize(600, 800);
        final int X = 300;
        Label codeLabel = getLabel("code:", X, 200);
        TextField codeTextField = getTextFieldDefault(offCode.getOffCode(), X, 240);
        codeTextField.setText(offCode.getOffCode());
        codeTextField.setEditable(false);

        Label beginDateLabel = getLabel("begin date", X, 290);
        Label beginDateError = getErrorLabel("", X, 310);
        DatePicker beginDatePicker = new DatePicker();
        beginDatePicker.setValue(toLocalDate(offCode.getBeginDate()));
        setPlace(beginDatePicker, X, 330);

        Label endDateLabel = getLabel("end date", X, 380);
        Label endDateError = getErrorLabel("", X, 400);
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setValue(toLocalDate(offCode.getEndDate()));
        setPlace(endDatePicker, X, 420);

        Label percentLabel = getLabel("percent", X, 470);
        Slider percentSlider = new Slider(1, 99, 1);
        Label percentSliderAmount = new Label("");
        setPlace(percentSliderAmount, X + 120, 490);
        setPlace(percentSlider, X, 510);
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
        percentSlider.setValue(offCode.getOffPercentage());
        percentSliderAmount.setText(Integer.toString(offCode.getOffPercentage()));

        Label maxOffLabel = getLabel("max off", X, 560);
        Slider maxOffSlider = new Slider(250, 100000, 250);
        maxOffSlider.setMajorTickUnit(1000);
        maxOffSlider.setSnapToTicks(true);
        setPlace(maxOffSlider, X, 580);
        Label maxOffAmount = new Label("");
        maxOffSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                maxOffAmount.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        });
        setPlace(maxOffAmount, 400, 560);
        maxOffSlider.setValue(offCode.getMaxDiscount());
        maxOffAmount.setText(Double.toString(offCode.getMaxDiscount()));

        Label repeat = getLabel("repeatTimes", X, 650);
        ComboBox<Integer> repeatCombobox = new ComboBox();
        repeatCombobox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); // modify to infinite times?
        repeatCombobox.getSelectionModel().select(0);
        setPlace(repeatCombobox, X, 670);
        repeatCombobox.getSelectionModel().select(offCode.getRepeat() - 1); //note : index works like array-index

        TableView tv = getPeopleTableViewForDiscountCode((ArrayList<Account>) offCode.getAppliedAccounts());
        tv.setLayoutX(500);
        tv.setLayoutY(200);
        Label accountsError = getErrorLabel("", 860, 180);

        Label accountsLabel = getLabel("applied accounts", 500, 180);

        Button confirm = new Button("confirm");
        setPlace(confirm, 580, 670);
        confirm.setOnAction(actionEvent -> {
            Date startDate = null;
            LocalDate localStartDate = beginDatePicker.getValue();
            if (localStartDate != null) {
                Instant instant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
                startDate = Date.from(instant);
                beginDateError.setText("");
            } else {
                beginDateError.setText("please select start date");
            }
            Date endDate = null;
            LocalDate localEndDate = endDatePicker.getValue();
            if (localEndDate != null) {
                Instant instant = Instant.from(localEndDate.atStartOfDay(ZoneId.systemDefault()));
                endDate = Date.from(instant);
                endDateError.setText("");
            } else {
                endDateError.setText("please select end date");
            }

            if (endDate != null && startDate != null) {
                if (!endDate.after(startDate)) endDateError.setText("end date must be after start date");
                else {
                    beginDateError.setText("");
                    endDateError.setText("");
                }
            }

            if (selectedAccounts.size() == 0) accountsError.setText("must choose at lease 1 person");
            else accountsError.setText("");

            if (accountsError.getText().equals("") &&
                    endDateError.getText().equals("") &&
                    beginDateError.getText().equals("")) {
                managerController.editDiscountCode
                        (offCode,
                                selectedAccounts,
                                startDate,
                                endDate,
                                (int) percentSlider.getValue(),
                                maxOffSlider.getValue(),
                                repeatCombobox.getValue()
                        );
            }
        });
        pane.getChildren().addAll(
                codeLabel,
                codeTextField,
                beginDateLabel,
                beginDateError,
                beginDatePicker,
                endDateLabel,
                endDateError,
                endDatePicker,
                percentLabel,
                percentSlider,
                maxOffLabel,
                maxOffSlider,
                repeat,
                repeatCombobox,
                accountsError,
                accountsLabel,
                tv,
                confirm
        );
        return pane;
    }//v1

    public Pane getManageAllProductsPane() {

        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        TableView products = getManageProductsTableView();
        setPlace(products, 400, 300);

        Label label = getLabel("all products : ", 400, 270);

        pane.getChildren().addAll(label, products);
        return pane;
    }//v1

    public Pane getCreateDiscountCodePane() {
        ArrayList<Account> selectedAccounts = new ArrayList<>();
        Pane pane = new Pane();
        //pane.setPrefSize(1500, 800);
        final int X = 300;
        Label codeLabel = getLabel("discount code", X, 200);
        TextField codeTextField = getTextFieldDefault("", X, 240);
        codeTextField.setEditable(false);

        Label startDateLabel = getLabel("start date: ", X, 290);
        Label startDateError = getErrorLabel("", X, 310);
        DatePicker startDatePicker = new DatePicker();
        setPlace(startDatePicker, X, 330);
        startDatePicker.setEditable(false);

        Label endDateLabel = getLabel("end date: ", X, 380);
        Label endDateError = getErrorLabel("", X, 400);
        DatePicker endDatePicker = new DatePicker();
        setPlace(endDatePicker, X, 420);
        endDatePicker.setEditable(false);

        Label percentLabel = getLabel("percent", X, 450);
        Slider percentSlider = new Slider(1, 99, 1);

        Label percentSliderAmount = new Label("");
        setPlace(percentSliderAmount, X + 120, 450);
        setPlace(percentSlider, X, 470);
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

        Label maxOffLabel = getLabel("max off", X, 540);
        Slider maxOffSlider = new Slider(250, 100000, 250);
        maxOffSlider.setMajorTickUnit(1000);
        maxOffSlider.setSnapToTicks(true);
        setPlace(maxOffSlider, X, 560);
        Label maxOffAmount = new Label("");
        maxOffSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                maxOffAmount.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        });
        setPlace(maxOffAmount, 400, 540);

        Label repeat = getLabel("repeatTimes", X, 620);
        ComboBox<Integer> repeatCombobox = new ComboBox();
        repeatCombobox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); // modify to infinite times?
        repeatCombobox.getSelectionModel().select(0);
        setPlace(repeatCombobox, X, 640);

        Label accountsLabel = getLabel("accounts", 500, 180);
        TableView tv = getPeopleTableViewForDiscountCode(selectedAccounts);
        tv.setLayoutX(500);
        tv.setLayoutY(220);
        Label accountsError = getErrorLabel("", 500, 200);

        Button confirm = new Button("confirm");
        setPlace(confirm, 580, 670);
        confirm.setOnAction(ev -> {
            Date startDate = null;
            LocalDate localStartDate = startDatePicker.getValue();
            if (localStartDate != null) {
                Instant instant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
                startDate = Date.from(instant);
                startDateError.setText("");
            } else {
                startDateError.setText("please select start date");
            }

            Date endDate = null;
            LocalDate localEndDate = endDatePicker.getValue();
            if (localEndDate != null) {
                Instant instant = Instant.from(localEndDate.atStartOfDay(ZoneId.systemDefault()));
                endDate = Date.from(instant);
                endDateError.setText("");
            } else {
                endDateError.setText("please select end date");
            }

            if (endDate != null && startDate != null) {
                if (!endDate.after(startDate)) endDateError.setText("end date must be after start date");
                else {
                    startDateError.setText("");
                    endDateError.setText("");
                }
            }

            if (selectedAccounts.size() == 0) accountsError.setText("must choose at lease 1 person");
            else accountsError.setText("");

            if (
                    accountsError.getText().equals("") &&
                            endDateError.getText().equals("") &&
                            startDateError.getText().equals("")) {
                managerController.createDiscountCode
                        (selectedAccounts,
                                startDate,
                                endDate,
                                (int) percentSlider.getValue(),
                                maxOffSlider.getValue(),
                                repeatCombobox.getValue()
                        );
            }
        });

        pane.getChildren().addAll(
                codeLabel,
                codeTextField,
                startDateLabel,
                startDateError,
                startDatePicker,
                endDateLabel,
                endDateError,
                endDatePicker,
                percentLabel,
                percentSlider,
                maxOffLabel,
                maxOffSlider,
                repeat,
                repeatCombobox,
                tv,
                percentSliderAmount,
                maxOffAmount,
                confirm,
                accountsError,
                accountsLabel
        );
        return pane;
    }//v1

    public Pane getAllCategoriesPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);

        TableView tv = new TableView();

        ObservableList<Category> data
                = FXCollections.observableArrayList(
                managerController.getAllCategories());

        TableColumn categoryName = new TableColumn("name");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn select = new TableColumn("open");
        select.setCellValueFactory(new PropertyValueFactory<>("name"));

        Callback<TableColumn<Category, String>, TableCell<Category, String>> cellFactory
                = //
                new Callback<TableColumn<Category, String>, TableCell<Category, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Category, String> param) {
                        final TableCell<Category, String> cell = new TableCell<Category, String>() {

                            final Button open = new Button("open");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    open.setOnAction(event -> {
                                        Category category = getTableView().getItems().get(getIndex());
                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(getEditCategoryPane(category)));
                                        stage.show();
                                    });
                                    setGraphic(open);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        select.setCellFactory(cellFactory);
        tv.setItems(data);

        tv.getColumns().addAll(categoryName, select);
        pane.getChildren().addAll(tv);
        return pane;
    }//v1

    public TableView getPeopleTableViewForDiscountCode(ArrayList<Account> selectedAccounts) {
        //ArrayList<Account> selection = new ArrayList<>();
        TableView<Account> table = new TableView<>();
        ObservableList<Account> data
                = FXCollections.observableArrayList(
                managerController.getAllCustomers());

        TableColumn productName = new TableColumn("name");
        productName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn select = new TableColumn("select");
        select.setCellValueFactory(new PropertyValueFactory<>("firstName"));


        Callback<TableColumn<Account, String>, TableCell<Account, String>> cellFactory
                = //
                new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Account, String> param) {
                        final TableCell<Account, String> cell = new TableCell<Account, String>() {

                            final CheckBox checkBox = new CheckBox();
                            boolean firstTime = true;

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    if (firstTime) {
                                        checkBox.setSelected(selectedAccounts.contains(
                                                getTableView().getItems().get(getIndex())
                                        ));
                                        firstTime = false;
                                    }
                                    checkBox.setOnAction(event -> {
                                        Account product = getTableView().getItems().get(getIndex());
                                        if (checkBox.isSelected()) selectedAccounts.add(product);
                                        else selectedAccounts.remove(product);
                                        System.out.println(selectedAccounts);
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

    public Pane getEditCategoryPane(Category category) {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);
        final int X = 300;

        Label nameLabel = getLabel("category name", X, 300);
        Label nameError = getErrorLabel("", X, 320);
        TextField nameField = getTextFieldDefault(category.getName(), X, 340);

        Label parentLabel = getLabel("parent category", X, 390);
        Label parentError = getErrorLabel("", X, 410);
        ComboBox<Category> parentComboBox = new ComboBox<>();
        parentComboBox.getItems().addAll(Category.getAllCategories());
        if (category.getParentCategory() != null)
            parentComboBox.getSelectionModel().select(category.getParentCategory());
        setPlace(parentComboBox, X, 430);

        ArrayList<Category> subCategoriesArrayList = new ArrayList<>();
        for (Map.Entry<Integer, Category> map : category.getSubCategories().entrySet()) {
            subCategoriesArrayList.add(map.getValue());
        }
        Label subCategoriesLabel = getLabel("sub categories", X + 200, 300);
        Label subCategoriesError = getErrorLabel("", X + 200, 320);
        TableView<Category> subCategoriesTableView = getSubCategoriesTableView(subCategoriesArrayList);
        setPlace(subCategoriesTableView, X + 200, 340);

        ArrayList<Product> products = new ArrayList<>();
        Label productsLabel = getLabel("products", X + 700, 300);
        Label productsError = getErrorLabel("", X + 700, 320);
        TableView productsTableViewCategory = getProductsTableViewCategory(category, products);
        setPlace(productsTableViewCategory, X + 700, 340);

        Button confirm = getButton("confirm", event -> {
            if (nameField.getText().equals("") || nameField.getText().equals(category.getName())) {
                nameError.setText("");
            } else {
                if (managerController.isCategoryValid(nameField.getText())) {
                    nameError.setText("this name is taken");
                }
            }

            Category parent = parentComboBox.getValue();
            if (parent == null) parentError.setText("");
            else if (parent.equals(category)) parentError.setText("a category cannot be its own father");
            else if (category.getSubCategories().containsValue(parent)) {
                parentError.setText("cannot select this category");
            } else {
                parentError.setText("");
            }

            try {
                getSubCategoriesError(category, subCategoriesArrayList);
                subCategoriesError.setText("");
            } catch (Exception e) {
                subCategoriesError.setText(e.getMessage());
            }

            if (products.size() < 1) {
                productsError.setText("must choose at least one");
            } else {
                productsError.setText("");
            }

            if (nameError.equals("") &&
                    parentError.equals("") &&
                    productsError.equals("") &&
                    subCategoriesError.equals("")) {
                String name = category.getName();
                if (nameField.getText() != null) name = nameField.getText();
                managerController.editCategory(category, products, name, parentComboBox.getValue());
            }
        });
        setPlace(confirm, X, 480);

        pane.getChildren().addAll(
                nameLabel,
                nameError,
                nameField,
                parentLabel,
                parentError,
                parentComboBox,
                subCategoriesLabel,
                subCategoriesError,
                subCategoriesTableView,
                productsLabel,
                productsError,
                productsTableViewCategory,
                confirm
        );

        return pane;
    }//v1 1

    public Pane getCreateCategoryPane() {
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);
        final int X = 300;

        HashMap<Object, String> attributes = new HashMap<>();

        Label name = getLabel("category name", 300, 300);
        Label nameError = getErrorLabel("", 300, 320);
        TextField nameField = getTextFieldDefault("", 300, 340);

        Label countLabel = getLabel("attributes", X, 370);
        Slider countSlider = new Slider(1, 15, 1);
        Label countSliderAmount = new Label("");
        setPlace(countSliderAmount, X + 120, 390);
        setPlace(countSlider, X, 410);
        countSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                countSliderAmount.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        });

        Label parentLabel = getLabel("parent", X, 460);
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(managerController.getAllCategories());
        setPlace(categoryComboBox, X, 480);

        Button ok = getButton("attributes", event -> {
            Stage stage = new Stage();
            stage.setScene(new Scene(getEnterAttributesTableView(attributes, (int) countSlider.getValue())));
            stage.show();
        });
        setPlace(ok, X, 550);

        ArrayList<Attributes> attributes1 = new ArrayList<>();
        for (String attributeName : attributes.values()) {
            attributes1.add(new Attributes(attributeName));
        }

        Button done = getButton("done", event -> {
            if (nameField.getText().equals("")) {
                nameError.setText("please selet a name");
            } else if (managerController.isCategoryValid(nameField.getText())) {
                nameError.setText("this name is taken");
            } else {
                if (attributes.size() < (int) countSlider.getValue()) {
                    nameError.setText("please enter all attributes");
                } else {
                    managerController.addCategory(nameField.getText(), categoryComboBox.getValue().getName(), attributes1);
                    nameError.setText("");
                }
            }
        });

        setPlace(done, X + 100, 550);
        pane.getChildren().addAll(name, nameError, nameField,
                countLabel, countSlider, countSliderAmount, parentLabel, categoryComboBox, ok, done);
        return pane;
    }//v1

    public TableView getEnterAttributesTableView(HashMap<Object, String> map, int size) {
        TableView<Object> tableView = new TableView<>();

        ArrayList<Object> objects = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            objects.add(new Object());
        }
        ObservableList<Object> data
                = FXCollections.observableArrayList(
                objects);

        TableColumn name = new TableColumn("name");

        Callback<TableColumn<Object, String>, TableCell<Object, String>> cellFactory
                = //
                new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Object, String> param) {
                        final TableCell<Object, String> cell = new TableCell<Object, String>() {

                            final TextField textField = new TextField();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    textField.setOnKeyTyped(event -> {
                                        Object o = getTableView().getItems().get(getIndex());
                                        map.put(o, textField.getText());
                                        System.out.println(map);
                                    });
                                    setGraphic(textField);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        name.setCellFactory(cellFactory);
        tableView.setItems(data);
        tableView.getColumns().addAll(name);
        return tableView;
    }//v1

    public Pane getManageUsersPane() {
        Pane pane = new Pane();
        TableView tv = getManageUsersTableView();
        pane.getChildren().addAll(tv);
        return pane;
    }//v1

    private void getSubCategoriesError(Category category, ArrayList<Category> subCategories) throws Exception {
        for (Category c : subCategories) {
            getSubCategoriesError(category, new ArrayList<>(c.getSubCategories().values()));
        }
        if (subCategories.contains(category)) throw new Exception("category cannot be a father");
    }

    private TableView getProductsTableViewCategory(Category category, ArrayList<Product> products) {

        TableView<Product> tableView = new TableView<>();

        ArrayList<Product> categoryProducts = new ArrayList<>();
        for (Product p : Product.getAllProducts()) {
            if (p.getParentCategory().equals(category)) {
                categoryProducts.add(p);
            }
        }

        ObservableList<Product> data
                = FXCollections.observableArrayList(
                Product.getAllProducts());

        TableColumn categoryName = new TableColumn("name");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn select = new TableColumn("select");
        select.setCellValueFactory(new PropertyValueFactory<>("name"));

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
                                if (!empty) {
                                    if (firstTime) {
                                        Product product = getTableView().getItems().get(getIndex());
                                        if (category != null) {
                                            if (categoryProducts.contains(product)) checkBox.setSelected(true);
                                        }
                                        firstTime = false;
                                    }
                                }
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    checkBox.setOnAction(event -> {
                                        Product product = getTableView().getItems().get(getIndex());
                                        if (checkBox.isSelected()) categoryProducts.add(product);
                                        else categoryProducts.remove(category);
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
        tableView.setItems(data);
        tableView.getColumns().addAll(categoryName, select);

        return tableView;
    }

    private TableView<Category> getSubCategoriesTableView(ArrayList<Category> subCategories) {
        TableView<Category> tableView = new TableView<>();

        ObservableList<Category> data
                = FXCollections.observableArrayList(
        );

        TableColumn categoryName = new TableColumn("name");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.setItems(data);
        tableView.getColumns().addAll(categoryName);

        return tableView;
    }

    public Pane getMoneySettingPane(){
        Pane pane = new Pane();
        Label label = getLabel("interest" , 100 , 80);
        Label label1 = getLabel("minimum remaining" , 300 , 80);
        Slider percentSlider = new Slider(1, 99, 1);
        Label percentSliderAmount = new Label("");
        setPlace(percentSlider , 100 , 100);
        setPlace(percentSliderAmount, 100 , 120);
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

        Slider minRemain = new Slider(1, 99, 1);
        Label minRemainLabel = new Label("");
        setPlace(minRemain , 300 , 100);
        setPlace(minRemainLabel, 300 , 120);
        minRemain.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                percentSliderAmount.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        });

        getButton("done" , event -> {
            //whatever should be done
        });

        pane.getChildren().addAll(
                label,
                label1,
                percentSlider,
                percentSliderAmount,
                minRemain,
                minRemainLabel
        );
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

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}