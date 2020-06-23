package view.Profile.SellerMenu;

import control.Controller;
import control.ManagerController;
import control.SellerController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.People.Account;
import model.People.Manager;
import model.People.Seller;

import javax.swing.*;

public class SellerMenuPanes {

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
                back,
                submit
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



}
