package view.Profile.SellerMenu;

import control.Controller;
import control.ManagerController;
import control.SellerController;
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
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Auction;
import model.OffCode;
import model.People.Account;
import model.People.Manager;
import model.People.Seller;
import model.Product;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellerMenuPanes {
    SellerController sellerController = new SellerController(Controller.getCurrentAccount());

    public static Pane getPersonalInfoPane (){
        Seller currentAccount = (Seller) Controller.getCurrentAccount();
        SellerController sellerController = new SellerController(currentAccount);
        final int X = 300;
        Pane pane = new Pane();
        pane.setPrefSize(1540 , 800);
        Label usernameLabel = getLabel("username" , X , 60);
        TextField username = getTextFieldDefault( currentAccount.getUsername(), 300 , 100);
        username.setEditable(false);
        Label passwordLabel = getLabel("password" , X , 150);
        Label passwordFieldError = getErrorLabel("" , X , 170);
        PasswordField passwordField = new PasswordField();
        setPlace(passwordField , X , 190);
        Label     confirmPasswordFieldLabel= getLabel("confirm new pass" , X , 240);
        Label         confirmPasswordFieldError= getErrorLabel("" , X , 260);
        PasswordField confirmPasswordField     = new PasswordField();
        setPlace(confirmPasswordField , X ,280);
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
        Label     brandLabel            = getLabel("brand" , X, 690);
        Label     brandError            =getErrorLabel("" , X , 710);
        TextField brandTextField        =getTextFieldDefault(currentAccount.getBrandName() , X , 730);
        Button submit = new Button("submit");
        EventHandler submitButtonAction = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!nameTextField.getText().equals(currentAccount.getFirstName())){
                    if (nameTextField.getText().equals("")) nameError.setText("");
                    else {
                        try {
                            sellerController.editFirstName(nameTextField.getText());
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
                        sellerController.changePassword (passwordField.getText());
                    }
                }
                if (!lastNameTextField.getText().equals(currentAccount.getLastName())){
                    if (lastNameTextField.getText().equals("")) lastNameError.setText("");
                    else {try {
                        sellerController.editLastName(lastNameTextField.getText());
                        lastNameError.setText("");
                    } catch (Exception e) {
                        lastNameError.setText(e.getMessage());
                    }}
                }
                if (!emailTextField.getText().equals(currentAccount.getEmail())){
                    if (emailTextField.getText().equals("")) emailError.setText("");
                    else {try {
                        sellerController.editEmail(emailTextField.getText());
                        emailError.setText("");
                    } catch (Exception e) {
                        emailError.setText(e.getMessage());
                    }}
                }
                if (!phoneNumberTextField.getText().equals(currentAccount.getPhoneNumber())){
                    if (phoneNumberTextField.getText().equals("")) phoneNumberError.setText("");
                    else {try {
                        sellerController.editPhoneNumber(nameTextField.getText());
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
                back,
                submit
        );
        return pane;
    }

    public Pane getCreateAuctionPane(){
        Pane pane = new Pane();
        pane.setPrefSize(1540,800);

        final int X = 300;
        ArrayList<Product> selected = new ArrayList<>();
//        Label nameLabel = getLabel("name" , X ,300);
//        Label nameError = getErrorLabel("",X,320);
//        TextField nameField = getTextFieldDefault("" , X , 340);

        Label       beginDateLabel = getLabel("begin date" , X , 390);
        Label       beginDateError = getErrorLabel("" , X ,410);
        DatePicker  beginDatePicker = new DatePicker();
        setPlace(beginDatePicker , X , 430);

        Label       endDateLabel = getLabel("end date" , X ,480);
        Label       endDateError = getErrorLabel("" , X,500);
        DatePicker  endDatePicker= new DatePicker();
        setPlace(endDatePicker , X , 520);

        Label       percentLabel     = getLabel("percent" , X , 570);
        Slider      percentSlider   = new Slider(1,99,1);
        Label percentSliderAmount = new Label("");
        setPlace(percentSliderAmount , X +120, 590);
        setPlace(percentSlider , X , 610);
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

        Label selectedLabel = getLabel("selected products" , 500 , 300);
        Label selectedError = getErrorLabel("" , 500 , 320);

        TableView products = getProductsTableViewAuction(selected);
        setPlace(products , 500 , 340);

        Button back = getButton("back" , event -> {
            //todo go back
        });
        setPlace(back , 330 , 650);

        Button confirm = getButton("confirm" ,event -> {
//            if (nameLabel.getText().length()==0){
//                nameError.setText("please select a name");
//            }else if(sellerController.doesAuctionExist(nameField.getText())){
//                nameError.setText("this name is taken");
//            }else{
//                nameError.setText("");
//            }

            if(beginDatePicker.getValue().equals(null)){
                beginDateError.setText("please select a date");
            }
            if (endDatePicker.getValue().equals(null)){
                endDateError.setText("please select a date");
            }
            if (endDatePicker.getValue()!=null&&beginDatePicker.getValue()!=null){
                if (endDatePicker.getValue().isAfter(beginDatePicker.getValue())){
                    endDateError.setText("end date must be after start");
                }else{
                    endDateError.setText("");
                    beginDateError.setText("");
                }
            }

            if (selected.size()<1){
                selectedError.setText("select at least one product");
            }else {
                selectedError.setText("");
            }

            if (//nameError.equals("")&&
                beginDateError.equals("")&&
                endDateError.equals("")&&
                selectedError.equals("")){
                new Auction((Seller) Controller.getCurrentAccount(),
                        toDate(beginDatePicker.getValue()),
                        toDate(endDatePicker.getValue()),
                        selected,
                        (int)percentSlider.getValue());
            }
        });
        pane.getChildren().addAll(
//                nameLabel,
//                nameError,
//                nameField,
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
                products
        );

        return pane;
    }

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

    public Pane getEditAuctionPane (Auction auction){
        Pane pane = new Pane();
        pane.setPrefSize(1540,800);

        final int X = 300;
        ArrayList<Product> selected = new ArrayList<>();

//        Label nameLabel = getLabel("name" , X ,300);
//        Label nameError = getErrorLabel("",X,320);
//        TextField nameField = getTextFieldDefault("" , X , 340);
//        nameField.setText(auction.getAuctionId());

        Label       beginDateLabel = getLabel("begin date" , X , 390);
        Label       beginDateError = getErrorLabel("" , X ,410);
        DatePicker  beginDatePicker = new DatePicker();
        setPlace(beginDatePicker , X , 430);
        beginDatePicker.setValue(toLocalDate(auction.getBeginDate()));

        Label       endDateLabel = getLabel("end date" , X ,480);
        Label       endDateError = getErrorLabel("" , X,500);
        DatePicker  endDatePicker= new DatePicker();
        setPlace(endDatePicker , X , 520);
        endDatePicker.setValue(toLocalDate(auction.getEndDate()));

        Label       percentLabel     = getLabel("percent" , X , 570);
        Slider      percentSlider   = new Slider(1,99,1);
        Label percentSliderAmount = new Label("");
        setPlace(percentSliderAmount , X +120, 590);
        setPlace(percentSlider , X , 610);
        percentSliderAmount.setText(Integer.toString((int) auction.getOffPercentage()));
        percentSlider.setValue(auction.getOffPercentage());
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

        Label selectedLabel = getLabel("selected products" , 500 , 300);
        Label selectedError = getErrorLabel("" , 500 , 320);

        TableView products = getProductsTableViewAuction(selected);
        setPlace(products , 500 , 340);

        Button back = getButton("back" , event -> {
            //todo go back
        });
        setPlace(back , 330 , 650);

        Button confirm = getButton("confirm" ,event -> {
//            if (nameLabel.getText().equals("")||nameField.getText().equals(auction.getAuctionId())){
//                nameError.setText("");
//            }else if(sellerController.doesAuctionExist(nameField.getText())){
//                nameError.setText("this name is taken");
//            }else{
//                nameError.setText("");
//            }

            if(beginDatePicker.getValue().equals(null)){
                beginDateError.setText("please select a date");
            }
            if (endDatePicker.getValue().equals(null)){
                endDateError.setText("please select a date");
            }
            if (endDatePicker.getValue()!=null&&beginDatePicker.getValue()!=null){
                if (endDatePicker.getValue().isAfter(beginDatePicker.getValue())){
                    endDateError.setText("end date must be after start");
                }else{
                    endDateError.setText("");
                    beginDateError.setText("");
                }
            }

            if (selected.size()<1){
                selectedError.setText("select at least one product");
            }else {
                selectedError.setText("");
            }

            if (//nameError.getText().equals("")&&
                    beginDateError.getText().equals("")&&
                    endDateError.getText().equals("")&&
                    selectedError.getText().equals("")){
                    sellerController.editAuction(auction,
                            toDate(beginDatePicker.getValue()) , toDate(endDatePicker.getValue()) ,selected,(int)percentSlider.getValue());
            }
        });
        pane.getChildren().addAll(
//                nameLabel,
//                nameError,
//                nameField,
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
                products
        );
        return pane;

    }

    private static TextField getTextFieldDefault(String Default , double x , double y){
        TextField textField = new TextField();
        textField.setText(Default);
        textField.setLayoutY(y);
        textField.setLayoutX(x);
        return textField;
    }

    private static Label getLabel (String text , double x , double y){
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private static Label getLabel (String text , double x , double y , Color color){
        Label label = new Label(text);
        label.setTextFill(color);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private static Label getErrorLabel (String text , double x , double y){
        Label label = new Label(text);
        label.setTextFill(Color.RED);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private static Button getButton(String text, EventHandler ev){
        Button button = new Button(text);
        button.setOnAction(ev);
        return button;
    }

    private static Node setPlace (Node w , double x , double y){
        w.setLayoutY(y);
        w.setLayoutX(x);
        return w;
    }

    private LocalDate toLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date toDate (LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
