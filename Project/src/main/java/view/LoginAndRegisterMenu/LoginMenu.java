package view.LoginAndRegisterMenu;

import control.Controller;
import control.Exceptions.InvalidUsernameException;
import control.Exceptions.WrongPasswordException;
import model.People.Manager;
import view.MainMenu;
import view.Menu;

public class LoginMenu extends Menu {

    String user;
    String pass;
    Controller controller;

    public LoginMenu(Menu parentMenu) {
        super("Login", parentMenu);
        controller = new Controller(Controller.getCurrentAccount());
        subMenus.put(1, new Menu("Enter Username", this) {
            @Override
            public void executeMenu() {
                getUsername();
            }
        });
    }

    @Override
    public void showMenu() {

    }

    public void executeMenu() {
        getUsername();
    }


    private void getUsername() {
        user = inputInFormat("Please Enter Username :", "\\w+");
        if (user.equalsIgnoreCase("back")) return;
        try {
            Manager.getAccountById(user);
        } catch (InvalidUsernameException e) {
        } finally {
            getPass();
        }
    }

    private void getPass() {
        pass = inputInFormat("Please Enter Password :", "\\w+");
        if (pass.equalsIgnoreCase("back")) getPass();
        try {
            Controller.login(user, pass);
            System.out.println("Logged In Successfully !");
            Thread.sleep(1000);
            goToNextMenu();
        } catch (WrongPasswordException | InvalidUsernameException e) {
            System.err.println("Username Or Password Is Wrong Or Your Account is Pending !");
            getUsername();
        } catch (InterruptedException e) {

        }
    }

    private void goToNextMenu() {
        Menu nextMenu = new MainMenu();
        nextMenu.showMenu();
        nextMenu.executeMenu();
    }


}
