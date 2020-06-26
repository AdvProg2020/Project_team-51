package main;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import control.Controller;
import control.Exceptions.InvalidUsernameException;
import control.Exceptions.WrongPasswordException;
import control.ManagerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;
import model.Requests.AddSellerRequest;

import java.io.IOException;

public class LoginDialog extends AnchorPane {

    @FXML
    private ComboBox<String> accountType;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextField usernameRegister;

    @FXML
    private JFXTextField passwordRegister;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXTextField balance;

    @FXML
    private JFXTextField company;

    @FXML
    private JFXButton registerButton;

    @FXML
    private Label registerError;

    @FXML
    private JFXTextField usernameLogin;

    @FXML
    private JFXPasswordField passwordLogin;

    @FXML
    private Label loginError;

    @FXML
    private JFXButton loginButton;


    private String type;
    private String registerUsername;
    private String registerPassword;
    private String loginUsername;
    private String loginPassword;
    private String firstNameText;
    private String lastNameText;
    private String emailText;
    private String phoneNumberText;
    private String balanceText;
    private String companyName;

    private StackPane rootPane;


    public LoginDialog(StackPane stackPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_dialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.rootPane = stackPane;
        this.getStylesheets().add(Main.class.getResource("login_dialog.css").toExternalForm());
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Customer", "Seller");
        if (!ManagerController.isThereAnyManager())
            items.add("Manager");

        type = "Customer";
        accountType.setItems(items);
        accountType.getSelectionModel().select("Customer");
        accountType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                if (newValue.equals("Seller"))
                    company.setVisible(true);
                else
                    company.setVisible(false);
                type = newValue;
            }
        });

        initialize();
    }

    @FXML
    public void initialize() {
        // login
        usernameLogin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loginUsername = newValue;
            }
        });
        passwordLogin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loginPassword = newValue;
            }
        });
        loginButton.setOnMouseClicked(e -> {
            if (!loginPassword.equals("") && !loginUsername.equals("")) {
                try {
                    Controller.login(loginUsername, loginPassword);
                    showConfirmation("You logged in successfully!");
                } catch (WrongPasswordException ex) {
                    showError("Wrong Password");
                } catch (InvalidUsernameException ex) {
                    showError("Invalid Username");
                }
            }
        });

        // register
        usernameRegister.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                registerUsername = newValue;
            }
        });
        passwordRegister.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                registerPassword = newValue;
            }
        });
        firstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                firstNameText = newValue;
            }
        });
        lastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lastNameText = newValue;
            }
        });
        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                emailText = newValue;
            }
        });
        phoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                phoneNumberText = newValue;
            }
        });
        balance.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                balanceText = newValue;
            }
        });
        company.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                companyName = newValue;
            }
        });
        registerButton.setOnAction(e -> {
            if (readyToRegister()) {
                if (type.equals("Customer")) {
                    Controller.setCurrentAccount(new Customer(registerUsername, registerPassword, firstNameText, lastNameText,
                            Double.parseDouble(balanceText), emailText, phoneNumberText));
                    showConfirmation("You registered successfully");
                } else if (type.equals("Seller")) {
                    new AddSellerRequest(new Seller(registerUsername, registerPassword, firstNameText, lastNameText,
                            Double.parseDouble(balanceText), emailText, phoneNumberText, companyName));
                    showConfirmation("You registered successfully! Request sent to Manager !");
                }
                if (type.equals("Manager")) {
                    Controller.setCurrentAccount(new Manager(registerUsername, registerPassword, firstNameText, lastNameText
                            , emailText, phoneNumberText));
                    showConfirmation("Manager account has created successfully!");
                }
            }
        });
    }


    private boolean readyToRegister() {
        return !firstNameText.isEmpty() && !lastNameText.isEmpty() && !registerUsername.isEmpty() && !registerPassword.isEmpty()
                && !emailText.isEmpty() && !phoneNumberText.isEmpty() && !balanceText.isEmpty();
    }


    private void showError(String message) {
        showErrorWithColor(message, "#fe615a");
    }

    @FXML
    private void showErrorWithColor(String message, String color) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton(" OK ");
        button.setStyle("-fx-background-color:" + color + "; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(255,104,110,0.64)");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        this.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> this.setEffect(null));
    }


    @FXML
    private void showConfirmation(String message) {
        BoxBlur boxBlur = new BoxBlur(4, 4, 4);
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #6d8040; -fx-background-radius:  18; -fx-text-fill: white");
        button.setPadding(new Insets(3, 16, 3, 16));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(message));
        dialogLayout.setStyle("-fx-background-color: rgba(118,160,93,0.64)");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        button.setOnMouseClicked((MouseEvent event) -> {
            dialog.close();
        });
        dialogLayout.setActions(button);
        dialog.show();
        this.setEffect(boxBlur);
        dialog.setOnDialogClosed((JFXDialogEvent event) -> this.setEffect(null));
    }

    private static void playAudio(String musicFile) {
//        AudioClip audioClip = new AudioClip(String.valueOf(Main.class.getResource(musicFile)));
//        audioClip.setCycleCount(Integer.MAX_VALUE);
//        audioClip.play();
    }
}
