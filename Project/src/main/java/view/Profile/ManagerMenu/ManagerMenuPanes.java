package view.Profile.ManagerMenu;

import control.Controller;
import control.Exceptions.WrongFormatException;
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
import model.People.Account;
import model.People.Customer;
import model.People.Manager;
import model.Product;
import model.Requests.Request;
import view.Menu;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerMenuPanes{
    ManagerController managerController = new ManagerController(Controller.getCurrentAccount());
    Account currentAccount = Controller.getCurrentAccount();

    public Pane getPersonalInfoPane (){
        final int X = 300;
        Pane pane = new Pane();
        pane.setPrefSize(1540 , 800);
        Label usernameLabel = getLabel("username" , X , 60);
        Label userNameError = getErrorLabel("" , X , 80);
        TextField username = getTextFieldDefault( currentAccount.getUsername(), 300 , 100);
        Label passwordLabel = getLabel("password" , X , 150);
        Label passwordFieldError = getErrorLabel("" , X , 170);
        PasswordField passwordField = new PasswordField();
        setPlace(passwordField , X , 190);
        PasswordField confirmPasswordField     = new PasswordField();
        Label         confirmPasswordFieldError= getErrorLabel("" , X , 260);
        TextField     confirmPasswordFieldLabel= getTextFieldDefault("confirm new pass" , X , 280);
        Label     nameLabel             = getLabel("name" , X , 330);
        Label     nameError             = getErrorLabel("" , X , 350);
        TextField nameTextField         = getTextFieldDefault(currentAccount.getFirstName() , X , 370);
        Label     lastNameLabel         = getLabel("last name" , X , 420);
        Label     lastNameError         = getErrorLabel("" , X , 440);
        TextField lastNameTextField     = getTextFieldDefault(currentAccount.getLastName() , X , 460);
        Label     emailLabel            = getLabel("email" , X , 510);
        Label     emailError            = getErrorLabel("" , X , 530);
        TextField emailTextField        = getTextFieldDefault(currentAccount.getEmail() , X , 550);
        Label     phoneNumberLabel      = getLabel("phone number" , X , 600);
        Label     phoneNumberError      = getErrorLabel("" , X , 620);
        TextField phoneNumberTextField  = getTextFieldDefault(currentAccount.getPhoneNumber() , X , 640);
        Button submit = new Button("submit");
        EventHandler submitButtonAction = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!nameTextField.getText().equals(currentAccount.getFirstName())){
                    if (nameTextField.getText().equals("")) nameError.setText("");
                    else {
                        try {
                        managerController.editFirstName(nameTextField.getText());
                        nameError.setText("");
                    } catch (Exception e) {
                        nameError.setText(e.getMessage());
                    }}
                }
                if (!passwordField.getText().equals(confirmPasswordField.getText())){
                    confirmPasswordFieldError.setText("passwords don't match");
                }
                if (passwordField.getText().equals(confirmPasswordField.getText())){
                    confirmPasswordFieldError.setText("");
                    if (passwordField.getText().equals("")){
                        passwordFieldError.setText("");
                    }
                    else if (passwordField.getText().length()<4) passwordFieldError.setText("password too short");
                    else {
                        passwordFieldError.setText("");
                        managerController.changePassword (passwordField.getText());
                    }
                }
                if (!lastNameTextField.getText().equals(currentAccount.getLastName())){
                    if (lastNameTextField.getText().equals("")) lastNameError.setText("");
                    else {try {
                        managerController.editLastName(lastNameTextField.getText());
                        lastNameError.setText("");
                    } catch (Exception e) {
                        lastNameError.setText(e.getMessage());
                    }}
                }
                if (!emailTextField.getText().equals(currentAccount.getEmail())){
                    if (emailTextField.getText().equals("")) emailError.setText("");
                    else {try {
                        managerController.editEmail(emailTextField.getText());
                        emailError.setText("");
                    } catch (Exception e) {
                        emailError.setText(e.getMessage());
                    }}
                }
                if (!phoneNumberTextField.getText().equals(currentAccount.getPhoneNumber())){
                    if (phoneNumberTextField.getText().equals("")) phoneNumberError.setText("");
                    else {try {
                        managerController.editPhoneNumber(nameTextField.getText());
                        phoneNumberError.setText("");
                    } catch (Exception e) {
                        phoneNumberError.setText(e.getMessage());
                    }}
                }
            }
        };
        Button back = new Button("back");
        back.setOnAction(ev->{
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

    public TableView getRequestsTebleView (){
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
                                        try {request.accept(); //todo allRequests remove this
                                        table.getItems().remove(request);}catch (Exception e){
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

    public TableView getManageProductsTableView (){

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
                                        try {managerController.removeProduct(product.getProductId()); //todo allProducts remove this
                                            table.getItems().remove(product);}catch (Exception e){
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
        table.getColumns().addAll(productName,delete);
        return table;
    }
    // note that this arraylist can be empty but will get filled by the table
    public TableView getProductsTableViewForOffCode (ArrayList<Product> selection){
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
        table.getColumns().addAll(productName,select);
        return table;
    }

    public TableView getPeopleTableViewForOffCode (List<Customer> selection){
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
        table.getColumns().addAll(productName,select);
        return table;
    }

//    public Pane getCreateOffCodePane(){
//        ArrayList<Product> selectedProducts = new ArrayList<>();
//        List<Customer> selectedCustomers = managerController.getAllCustomers();
//        final int X = 300;
//        Label       codeLabel     = getLabel("discount code" , X , 300);
//        Label       codeError     = getErrorLabel("" , X , 320);
//        TextField   codeTextField = getTextFieldDefault("" , X , 340);
//        Label       startDateLabel= getLabel("start date: " , X , 390);
//        Label       startDateError=getErrorLabel("" , X , 410);
//        DatePicker  startDatePicker=new DatePicker();
//        setPlace(startDatePicker , X , 430);
//        Label       endDateLabel = getLabel("end date: " , X , 480);
//        Label       endDateerror = getErrorLabel("" , X , 500);
//        DatePicker endDatePicker = new DatePicker();
//        setPlace(endDatePicker , X , 520);
//        Pane pane = new Pane();
//        pane.setPrefSize(1500,800);
//        TableView tv = getPeopleTableViewForOffCode(selectedCustomers);
//        tv.setLayoutX(500);
//        tv.setLayoutY(200);
//        TableView productTableView = getProductsTableViewForOffCode(selectedProducts);
//        productTableView.setLayoutX(800);
//        productTableView.setLayoutY(200);
//        pane.getChildren().addAll(
//                codeLabel,
//                codeError,
//                codeTextField,
//                startDateLabel,
//                startDateError,
//                startDatePicker,
//                endDateLabel,
//                endDateerror,
//                tv,
//                productTableView,
//                endDatePicker
//        );
//        return pane;
//    }

    public Pane getCreateDiscountCodePane (){
        ArrayList<Account> selectedAccounts = new ArrayList<>();
        Pane pane = new Pane();
        pane.setPrefSize(1500,800);
        final int X = 300;
        Label       codeLabel     = getLabel("discount code" , X , 200);
        Label       codeError     = getErrorLabel("" , X , 220);
        TextField   codeTextField = getTextFieldDefault("" , X , 240);

        Label       startDateLabel= getLabel("start date: " , X , 290);
        Label       startDateError=getErrorLabel("" , X , 310);
        DatePicker  startDatePicker=new DatePicker();
        setPlace(startDatePicker , X , 330);
        startDatePicker.setEditable(false);

        Label       endDateLabel = getLabel("end date: " , X , 380);
        Label       endDateError = getErrorLabel("" , X , 400);
        DatePicker endDatePicker = new DatePicker();
        setPlace(endDatePicker , X , 420);
        endDatePicker.setEditable(false);

        Label       percentLabel     = getLabel("percent" , X , 450);
        Slider      percentSlider   = new Slider(1,99,1);

        Label percentSliderAmount = new Label("");
        setPlace(percentSliderAmount , X +120, 450);
        setPlace(percentSlider , X , 470);
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

        Label       maxOffLabel     = getLabel("max off" , X , 540);
        Slider      maxOffSlider    =new Slider(250,100000,250);
        maxOffSlider.setMajorTickUnit(1000);
        maxOffSlider.setSnapToTicks(true);
        setPlace(maxOffSlider , X , 560);
        Label       maxOffAmount = new Label ("");
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
        setPlace(maxOffAmount,400 , 540);

        Label       repeat          = getLabel("repeatTimes" , X , 620);
        ComboBox<Integer>    repeatCombobox  = new ComboBox();
        repeatCombobox.getItems().addAll(1,2,3,4,5,6,7,8,9,10); // modify to infinite times?
        repeatCombobox.getSelectionModel().select(0);
        setPlace(repeatCombobox , X , 640);

        Label       accountsLabel = getLabel("accounts" , 800 , 180);
        TableView tv = getPeopleTableViewForDiscountCode(selectedAccounts);
        tv.setLayoutX(500);
        tv.setLayoutY(200);
        Label       accountsError = getErrorLabel("" , 860 , 180);

        Label       productsLabel = getLabel("products", 500 , 180 );

        Button back = new Button("back");
        setPlace(back , 500 , 670);
        back.setOnAction(ev->{
            //todo just go to the privious scene
        });//todo

        Button confirm = new Button("confirm");
        setPlace(confirm , 580 , 670);
        confirm.setOnAction(ev->{
            Date startDate=null;
            LocalDate localStartDate = startDatePicker.getValue();
            if(localStartDate!=null) {
                Instant instant = Instant.from(localStartDate.atStartOfDay(ZoneId.systemDefault()));
                startDate = Date.from(instant);
                startDateError.setText("");
            }
            else {
                startDateError.setText("please select start date");
            }

            Date endDate=null;
            LocalDate localEndDate = endDatePicker.getValue();
            if(localEndDate!=null) {Instant instant = Instant.from(localEndDate.atStartOfDay(ZoneId.systemDefault()));
                endDate = Date.from(instant);
                endDateError.setText("");
            }
            else {
                endDateError.setText("please select end date");
            }

            if (endDate!=null && startDate!=null) {
                if (!endDate.after(startDate)) endDateError.setText("end date must be after start date");
                else {
                    startDateError.setText("");
                    endDateError.setText("");
                }
            }

            if (codeTextField.getText().equals("")){
                codeError.setText("this field cannot be empty");
            }
            else {
                if (managerController.isCodeUsedBefore(codeTextField.getText())){
                    codeError.setText("this code is taken before");
                }
                else codeLabel.setText("");
            }

            if (selectedAccounts.size()==0)accountsError.setText("must choose at lease 1 person");
            else accountsError.setText("");

            if (
                accountsError.equals("")&&
                codeError.equals("")&&
                endDateError.equals("")&&
                startDateError.equals(""))
            {
                managerController.createDiscountCode
                        (selectedAccounts,
                                startDate,
                                endDate,
                                (int)percentSlider.getValue(),
                                maxOffSlider.getValue()
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
                productsLabel,
                accountsError,
                accountsLabel
        );
        return pane;
    }

//    private TableView getProductsTableViewForDiscountCode(ArrayList <Product> products) {
//
//        TableView<Product> table = new TableView<>();
//        ObservableList<Product> data
//                = FXCollections.observableArrayList(
//                Product.getAllProducts()); // must get data from manager controlle
//
//        TableColumn productName = new TableColumn("name");
//        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn select = new TableColumn("select");
//        select.setCellValueFactory(new PropertyValueFactory<>("uselessString"));
//
//
//        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory
//                = //
//                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
//                    @Override
//                    public TableCell call(final TableColumn<Product, String> param) {
//                        final TableCell<Product, String> cell = new TableCell<Product, String>() {
//
//                            final CheckBox checkBox = new CheckBox();
//
//                            @Override
//                            public void updateItem(String item, boolean empty) {
//                                super.updateItem(item, empty);
//                                if (empty) {
//                                    setGraphic(null);
//                                    setText(null);
//                                } else {
//                                    checkBox.setOnAction(event -> {
//                                        Product product = getTableView().getItems().get(getIndex());
//                                        if (checkBox.isSelected()) products.add(product);
//                                        else products.remove(product);
//                                        System.out.println(products);
//                                    });
//                                    setGraphic(checkBox);
//                                    setText(null);
//                                }
//                            }
//                        };
//                        return cell;
//                    }
//                };
//        select.setCellFactory(cellFactory);
//
//        table.setItems(data);
//        table.getColumns().addAll(productName,select);
//        return table;
//    }

    public TableView getPeopleTableViewForDiscountCode(ArrayList<Account> selectedAccounts){
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
        table.getColumns().addAll(productName,select);
        return table;
    }

    private TextField getTextFieldDefault(String Default , double x , double y){
        TextField textField = new TextField();
        textField.setText(Default);
        textField.setLayoutY(y);
        textField.setLayoutX(x);
        return textField;
    }

    private Label getLabel (String text , double x , double y){
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Label getLabel (String text , double x , double y , Color color){
        Label label = new Label(text);
        label.setTextFill(color);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Label getErrorLabel (String text , double x , double y){
        Label label = new Label(text);
        label.setTextFill(Color.RED);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Button getButton(String text, EventHandler ev){
        Button button = new Button(text);
        button.setOnAction(ev);
        return button;
    }

    private Node setPlace (Node w , double x , double y){
        w.setLayoutY(y);
        w.setLayoutX(x);
        return w;
    }
}
