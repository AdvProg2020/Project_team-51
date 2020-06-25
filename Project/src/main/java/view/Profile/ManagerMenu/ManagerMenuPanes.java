package view.Profile.ManagerMenu;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.Category;
import model.OffCode;
import model.People.Account;
import model.People.Customer;
import model.Product;
import model.Requests.Request;

import javax.persistence.Table;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ManagerMenuPanes {
    ManagerController managerController = new ManagerController(Controller.getCurrentAccount());
    Account currentAccount = Controller.getCurrentAccount();

    public Pane getPersonalInfoPane() {
        final int X = 300;
        Pane pane = new Pane();
        pane.setPrefSize(1540, 800);
        Label usernameLabel = getLabel("username", X, 160);
        Label userNameError = getErrorLabel("", X, 180);
        TextField username = getTextFieldDefault(currentAccount.getUsername(), X, 200);
        Label passwordLabel = getLabel("password", X, 250);
        Label passwordFieldError = getErrorLabel("", X, 270);
        PasswordField passwordField = new PasswordField();
        setPlace(passwordField, X, 290);
        Label confirmPasswordFieldLabel = getLabel("confirm new pass", X, 340);
        Label confirmPasswordFieldError = getErrorLabel("", X, 360);
        PasswordField confirmPasswordField = new PasswordField();
        setPlace(confirmPasswordField , X, 380);
        Label nameLabel = getLabel("name", X, 430);
        Label nameError = getErrorLabel("", X, 450);
        TextField nameTextField = getTextFieldDefault(currentAccount.getFirstName(), X, 470);
        Label lastNameLabel = getLabel("last name", X, 520);
        Label lastNameError = getErrorLabel("", X, 540);
        TextField lastNameTextField = getTextFieldDefault(currentAccount.getLastName(), X, 560);
        Label emailLabel = getLabel("email", X, 610);
        Label emailError = getErrorLabel("", X, 630);
        TextField emailTextField = getTextFieldDefault(currentAccount.getEmail(), X, 650);
        Label phoneNumberLabel = getLabel("phone number", X, 700);
        Label phoneNumberError = getErrorLabel("", X, 720);
        TextField phoneNumberTextField = getTextFieldDefault(currentAccount.getPhoneNumber(), X, 740);
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
                }
                if (!phoneNumberTextField.getText().equals(currentAccount.getPhoneNumber())) {
                    if (phoneNumberTextField.getText().equals("")) phoneNumberError.setText("");
                    else {
                        try {
                            managerController.editPhoneNumber(nameTextField.getText());
                            phoneNumberError.setText("");
                        } catch (Exception e) {
                            phoneNumberError.setText(e.getMessage());
                        }
                    }
                }
                //if (confirmPasswordFieldError.equals())
            }
        };
        Button back = new Button("back");
        back.setOnAction(ev -> {
            //todo go back
        });
        back.setLayoutX(300);
        back.setLayoutY(690);
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
                back,
                submit
        );
        return pane;
    }

    public Pane getManageRequestsPane(){
        Pane pane = new Pane();

        Label requestLabel = getLabel("requests",300,300);
        TableView tv = getRequestsTebleView();
        Button back = getButton("back" , event -> {
            // TODO: ۲۵/۰۶/۲۰۲۰ go back
        });
        setPlace(back,370,700);

        pane.getChildren().addAll(requestLabel,tv,back);
        return pane;
    }

    public TableView getManageUsersTableView (){
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Manager" , "Customer" , "Seller");
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
        balance.setCellValueFactory(new PropertyValueFactory<>("balanceString")); // todo create method in account

        TableColumn type = new TableColumn("type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn delete = new TableColumn("delete");
        delete.setCellValueFactory(new PropertyValueFactory<>("noUse"));


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
                                    btn.setOnAction(ev->{
                                        Account account = getTableView().getItems().get(getIndex());
                                        table.getItems().remove(account);
                                        try {
                                            managerController.deleteUser(account.getUsername()); //todo replace with manager delete account method
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
                                if (item!=null)comboBox.getSelectionModel().select(item);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    comboBox.setOnAction(event -> {
                                        Account account = getTableView().getItems().get(getIndex());
                                        if (!comboBox.getValue().equals(account.getType())){managerController.setAccountType (account,(String) comboBox.getValue() );
                                            System.out.println("combo" + Account.getAllAccounts());}
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
        table.getColumns().addAll(username,firstName , lastName , balance ,delete, type);
        return table;
    }

    private ComboBox getAccountTypeCombobox(){
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll("Manager" , "Customer" , "Seller");
        return comboBox;
    }

    public TableView getRequestsTebleView() {
        TableView<Request> table = new TableView<>();
        ObservableList<Request> data
                = FXCollections.observableArrayList(
                Request.getAllRequests());

        TableColumn requestDetail = new TableColumn("Detail");
        requestDetail.setCellValueFactory(new PropertyValueFactory<>("detail"));

        TableColumn accept = new TableColumn("accept");
        accept.setCellValueFactory(new PropertyValueFactory<>("noUse"));

        TableColumn decline = new TableColumn("decline");
        decline.setCellValueFactory(new PropertyValueFactory<>("noUse"));


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
                                            request.accept(); //todo allRequests remove this
                                            table.getItems().remove(request);
                                        } catch (Exception e) {
                                            //todo handle this error better: can reject automatically and show popup error
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
                                        request.delete();
                                        table.getItems().remove(request);
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
    }

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
                                            managerController.removeProduct(product.getProductId()); //todo allProducts remove this
                                            table.getItems().remove(product);
                                        } catch (Exception e) {
                                            //todo handle this error better: can reject automatically and show popup error
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
    }

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
    }

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
    }

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
                                        // changing the pane here to open this offcode page
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
    }

    public Pane getViewOffcodesPane(){
        Pane pane = new Pane();
        pane.setPrefSize(1540,800);

        Label label = getLabel("offCodes",300,300);
        TableView tableView = getAllOffCodesTableView();
        setPlace(tableView,300,320);
        Button back = getButton("back" , event -> {
            // TODO: ۲۵/۰۶/۲۰۲۰ go back
        });
        setPlace(back,370, 700);

        pane.getChildren().addAll(label,tableView,back);
        return pane;
    }

    public Pane getEditDiscountCodePane(OffCode offCode) {
        Pane pane = new Pane();
        List<Account> selectedAccounts;
        selectedAccounts = offCode.getAppliedAccounts();
        pane.setPrefSize(1540, 800);
        final int X = 300;
        Label codeLabel = getLabel("code:", X, 200);
        Label codeError = getErrorLabel("", X, 220);
        TextField codeTextField = getTextFieldDefault(offCode.getOffCode(), X, 240);
        codeTextField.setText(offCode.getOffCode());

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
        setPlace(maxOffAmount, 400, 600);
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

        Button back = new Button("back");
        setPlace(back, 500, 670);
        back.setOnAction(actionEvent -> {
            //todo
        });

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

            if (codeTextField.getText().equals("")) {
                codeError.setText("");
            } else {
                if (managerController.isCodeUsedBefore(codeTextField.getText())) {
                    codeError.setText("this code is taken before");
                } else codeLabel.setText("");
            }

            if (selectedAccounts.size() == 0) accountsError.setText("must choose at lease 1 person");
            else accountsError.setText("");

            if (accountsError.equals("") &&
                    codeError.equals("") &&
                    endDateError.equals("") &&
                    beginDateError.equals("")) {
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
                codeError,
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
                back,
                confirm
        );
        return pane;
    }

    public Pane getManageAllProductsPane (){

        Pane pane = new Pane();
        pane.setPrefSize(1540,800);

        TableView products = getManageProductsTableView();
        setPlace(products , 400 , 300);

        Label label = getLabel("all products : " , 400 , 270);

        Button back = getButton("back" , event -> {
            // TODO: ۲۴/۰۶/۲۰۲۰ going back
        });

        setPlace(back , 430 , 650);
        pane.getChildren().addAll(products , label , back);

        return pane;
    }

    public Pane getCreateDiscountCodePane() {
        ArrayList<Account> selectedAccounts = new ArrayList<>();
        Pane pane = new Pane();
        pane.setPrefSize(1500, 800);
        final int X = 300;
        Label codeLabel = getLabel("discount code", X, 200);
        Label codeError = getErrorLabel("", X, 220);
        TextField codeTextField = getTextFieldDefault("", X, 240);

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

        Button back = new Button("back");
        setPlace(back, 500, 670);
        back.setOnAction(ev -> {
            //todo just go to the privious scene
        });//todo

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

            if (codeTextField.getText().equals("")) {
                codeError.setText("this field cannot be empty");
            } else {
                if (managerController.isCodeUsedBefore(codeTextField.getText())) {
                    codeError.setText("this code is taken before");
                } else codeLabel.setText("");
            }

            if (selectedAccounts.size() == 0) accountsError.setText("must choose at lease 1 person");
            else accountsError.setText("");

            if (
                    accountsError.equals("") &&
                            codeError.equals("") &&
                            endDateError.equals("") &&
                            startDateError.equals("")) {
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
                codeError,
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
                back,
                percentSliderAmount,
                maxOffAmount,
                confirm,
                accountsError,
                accountsLabel
        );
        return pane;
    }

    public Pane getAllCategoriesPane(){
        Pane pane = new Pane();
        return pane;
    }

    public TableView getPeopleTableViewForDiscountCode(ArrayList<Account> selectedAccounts) {
        ArrayList<Product> selection = new ArrayList<>();
        TableView<Product> table = new TableView<>();
        ObservableList<Product> data
                = FXCollections.observableArrayList(
                Product.getAllProducts()); // must get data from manager controlle

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
    }

    public Pane getEditCategoryPane(Category category){
        Pane pane = new Pane ();
        pane.setPrefSize(1540,800);
        final int X = 300;

        Label nameLabel = getLabel("category name" , X , 300);
        Label nameError = getErrorLabel("" , X , 320);
        TextField nameField = getTextFieldDefault(category.getName(),X,340);

        Label parentLabel = getLabel("parent category" ,X,390);
        Label parentError = getErrorLabel("",X,410);
        ComboBox <Category> parentComboBox = new ComboBox<>();
        parentComboBox.getItems().addAll(Category.getAllCategories());
        if (category.getParentCategory()!=null)
            parentComboBox.getSelectionModel().select(category.getParentCategory());
        setPlace(parentComboBox , X,430);

        ArrayList<Category> subCategoriesArrayList = new ArrayList<>();
        for (Map.Entry<Integer , Category> map : category.getSubCategories().entrySet()){
            subCategoriesArrayList.add(map.getValue());
        }
        Label subCategoriesLabel = getLabel("sub categories" , X+200 ,300);
        Label subCategoriesError = getErrorLabel("",X+200,320);
        TableView<Category> subCategoriesTableView = getSubCategoriesTableView(subCategoriesArrayList);
        setPlace(subCategoriesTableView,X+200,340);

        ArrayList<Product> products = new ArrayList<>();
        Label productsLabel = getLabel("products",X+700,300);
        Label productsError = getErrorLabel("",X+700,320);
        TableView productsTableViewCategory = getProductsTableViewCategory(category,products);
        setPlace(productsTableViewCategory,X+700,340);

        Button back = getButton("back" , event -> {
            // TODO: ۲۵/۰۶/۲۰۲۰ go back
        });
        setPlace(back,X,480);

        Button confirm = getButton("confirm",event -> {
            if (nameField.getText().equals("")||nameField.getText().equals(category.getName())){
                nameError.setText("");
            }else {
                if (managerController.isCategoryValid(nameField.getText())){
                    nameError.setText("this name is taken");
                }
            }

            Category parent = parentComboBox.getValue();
            if (parent.equals(category)) parentError.setText("a category cannot be its own father");
            else if (category.getSubCategories().containsValue(parent)){
                parentError.setText("cannot select this category");
                // TODO: ۲۵/۰۶/۲۰۲۰ this must get all the sub categories from the subcategories too
            }
            else {
                parentError.setText("");
            }

            try {
                getSubCategoriesError(category , subCategoriesArrayList);
                subCategoriesError.setText("");
            } catch (Exception e) {
                subCategoriesError.setText(e.getMessage());
            }

            if (products.size()<1){
                productsError.setText("must choose at least one");
            }else {
                productsError.setText("");
            }

            if (nameError.equals("")&&
                    parentError.equals("")&&
                    productsError.equals("")&&
                    subCategoriesError.equals("")){

            }
        });


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
                productsTableViewCategory
        );

        return pane;
    }

    public Pane getCreateCategoryPane(){

        return null;
    }

    public Pane getManageUsersPane(){
        Pane pane = new Pane ();
        pane.setPrefSize(1540,800);


        Label label = getLabel("users" , 300,300);
        TableView tv = getManageUsersTableView();
        setPlace(tv,300,330);

        Button back = getButton("back",event -> {
            // TODO: ۲۵/۰۶/۲۰۲۰ going back
        });
        pane.getChildren().addAll(label,tv,back);
        return pane;

    }

    private void getSubCategoriesError(Category category , ArrayList<Category> subCategories) throws Exception {
        for (Category c : subCategories){
            getSubCategoriesError(category , new ArrayList<>(c.getSubCategories().values()));
        }
        if (subCategories.contains(category)) throw new Exception("category cannot be a father");
    }

    private TableView getProductsTableViewCategory(Category category , ArrayList<Product> products) {

        TableView <Product> tableView = new TableView<>();

        ArrayList<Product> categoryProducts = new ArrayList<>();
        for (Product p : Product.getAllProducts()){
            if (p.getParentCategory().equals(category)){
                categoryProducts.add(p);
            }
        }

        ObservableList<Product> data
                = FXCollections.observableArrayList(
                Product.getAllProducts());

        TableColumn categoryName = new TableColumn("name");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));

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
                                if (!empty){
                                    if (firstTime){
                                        Product product = getTableView().getItems().get(getIndex());
                                        if (category!=null){
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
        tableView.getColumns().addAll(categoryName,select);

        return tableView;
    }

    private TableView<Category> getSubCategoriesTableView(ArrayList<Category> subCategories) {
        TableView <Category> tableView = new TableView<>();

        ObservableList<Category> data
                = FXCollections.observableArrayList(
                Category.getAllCategories());

        TableColumn categoryName = new TableColumn("name");
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn select = new TableColumn("select");
        select.setCellValueFactory(new PropertyValueFactory<>("uselessString"));

        Callback<TableColumn<Category, String>, TableCell<Category, String>> cellFactory
                = //
                new Callback<TableColumn<Category, String>, TableCell<Category, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Category, String> param) {
                        final TableCell<Category, String> cell = new TableCell<Category, String>() {

                            final CheckBox checkBox = new CheckBox();
                            boolean firstTime = true;
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty){
                                    if (firstTime){
                                        Category category = getTableView().getItems().get(getIndex());
                                        if (category!=null){
                                            if (subCategories.contains(category)) checkBox.setSelected(true);
                                        }
                                        firstTime = false;
                                    }
                                }
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    checkBox.setOnAction(event -> {
                                        Category category = getTableView().getItems().get(getIndex());
                                        if (checkBox.isSelected()) subCategories.add(category);
                                        else subCategories.remove(category);
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
        tableView.getColumns().addAll(categoryName,select);

        return tableView;
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