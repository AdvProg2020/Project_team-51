package view.Profile.ManagerMenu;

import control.Controller;
import control.Exceptions.WrongFormatException;
import control.ManagerController;
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
import model.People.Manager;
import model.Requests.Request;
import view.Menu;

import java.text.ParseException;

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
