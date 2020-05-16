package view;

import control.Controller;
import control.Exceptions.*;
import control.ManagerController;
import model.People.Customer;
import model.People.Manager;
import model.People.Seller;

import java.util.regex.Pattern;

public class LoginMenu extends Menu {
    String type;
    String username,password,phone,email,firstName,lastName,number,user,pass;

    public LoginMenu(Menu parentMenu) {
        super("Login", parentMenu);
    }
    @Override
    public void showMenu() {
        System.out.println("enter 1 for login\n2 for signup\n0 for back");
    }

    @Override
    public void executeMenu() {
        while (true) {
            command = scanner.nextLine();
            if (command.equals("0")) {
                this.parentMenu.showMenu();
                this.parentMenu.executeMenu();
            }
            else if (command.equals("1")) login();
            else if (command.equals("2")) signup ();
        }
    }

    private void login() {
        String user,pass;

    }

    private void signup() {
        getType();
    }


    private static boolean doesMatch(String pattern, String anotherString) {
        return Pattern.compile(pattern).matcher(anotherString).find();
    }

    public void getType (){
        try{
            System.out.println("select account type :\n1.customer\n2.seller\n3.manager\n4.back");
            if ((command = scanner.nextLine()).equalsIgnoreCase("4")) {
            this.showMenu();
            this.executeMenu();
        }
            else if (command.equals("3")){
                if (ManagerController.isHeAbleToCreateManger())type =  "manager";
                else throw new Exception("you can't create manager");
    }
            else if (command.equals("2")) type = "seller";
            else if (command.equals("1")) type = "customer";
            else throw new WrongFormatException("input");
            getUsername();
            }catch (Exception e){
            System.err.println(e.getMessage());
            getType();
        }
    }

    public void getUsername () {
        try{
            System.out.println("please enter username OR back");
            if ((command = scanner.nextLine()).equalsIgnoreCase("back")) {
                getType();
            }
            if (doesMatch("\\W+",command)) throw new WrongFormatException("username");
            if (Manager.getAccountById(command)!=null) throw new UsernameAlreadyExistsException();
            else username = command;
            getPassword();
        }catch (Exception e){
            System.err.println(e.getMessage());
            getUsername();
        }
    }

    public void getPassword (){
        try {
            System.out.println("enter password OR back");
            if ((command = scanner.nextLine()).equalsIgnoreCase("back")) {
                getUsername();
            }
            if (doesMatch("\\W+",command)) throw new WrongFormatException("password");
            if (command.length()<4) throw new WeakPasswordException();
            password = command;
            getFirstName();
        }catch (Exception e){
            System.err.println(e.getMessage());
            getPassword();
        }
    }

    public void getFirstName(){
        try{
            System.out.println("please enter your name");
            if ((command = scanner.nextLine()).equalsIgnoreCase("back")) {
                getPassword();
            }
        if (doesMatch("\\W+",command)) throw new WrongFormatException("first name");
        firstName = command;
        getLastName();
        }catch (Exception e){
            System.err.println(e.getMessage());
            getFirstName();
        }
    }

    public void getLastName(){
        try{System.out.println("please enter your lastName");
            if ((command = scanner.nextLine()).equalsIgnoreCase("back")) {
                getFirstName();
            }
            if (doesMatch("\\W+",command)) throw new WrongFormatException("last name");
            lastName = command;
            getEmail();
        }catch (Exception e){
            System.err.println(e.getMessage());
            getLastName();
        }
    }

    public void getEmail(){
        try{
            System.out.println("enter Email OR back");
            if ((command = scanner.nextLine()).equalsIgnoreCase("back")) getLastName();
            if (!doesMatch("\\w+@w+\\.w+",command)) throw new WrongFormatException("email");
            email = command;
            getNumber();
        }catch (Exception e){
            System.err.println(e.getMessage());
            getEmail();
        }
    }

    public void getNumber (){
        try{
            System.out.println("enter phone number OR back to cancel");
            if ((command = scanner.nextLine()).equalsIgnoreCase("back")) getEmail();
            if (doesMatch("\\D",command)) throw new WrongFormatException("phone number");
            number = command;
            getBalance();
        }catch (Exception e){
            System.err.println(e.getMessage());
            getNumber();
        }
    }

    public void getBalance(){
        try{System.out.println("enter balance OR back");
        if ((command = scanner.nextLine()).equalsIgnoreCase("back"))getNumber();
        if (!doesMatch("^\\d+\\.\\d*$",command)) throw new WrongFormatException("balance amount");
        double balance = Double.parseDouble(command);
        switch (type){
            case "customer" : new Customer(username,password,firstName,lastName,balance,email,number);
            case "seller" : {
                System.out.println("please enter your brand name");
                String brand = scanner.nextLine();
                new Seller(username,password,firstName,lastName,balance,email,number,brand);
            }
            case "manager" : new Manager(username,password,firstName,lastName,balance,email,number);
        }
        this.showMenu();
        this.executeMenu();}catch (Exception e){
            System.err.println(e.getMessage());
            getBalance();
        }
    }

    public void enterUsername(){
        System.out.println("please enter username OR back");
        if ((user=scanner.nextLine()).equalsIgnoreCase("back")) {
            this.showMenu();
            this.executeMenu();
        }
            try{
                if (Manager.getAccountById(user)==null) throw new InvalidUsernameException();
                enterPass();
            }catch (Exception e){
                System.err.println(e.getMessage());
                enterUsername();
            }
    }

    public void enterPass (){
        System.out.println("please enter password or back");
        if ((pass=scanner.nextLine()).equalsIgnoreCase("back")) {
            getUsername();
        }
        try {
            if (!Controller.doesPasswordMatches(user,pass)) throw new InvalidPasswordException();
            Controller.setCurrentAccount(username);
// which menu should it open now ?
        }catch (Exception e){
            System.err.println(e.getMessage());
            enterPass();
        }
    }
}