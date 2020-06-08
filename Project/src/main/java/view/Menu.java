package view;

import control.Controller;
import control.Exceptions.HaveNotLoggedInException;
import view.LoginAndRegisterMenu.LoginAndRegisterMenu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {

    public static final Scanner scanner = new Scanner(System.in);
    public static String command = "";
    protected static Stack<Menu> menusHistory = new Stack<>();
    protected Menu parentMenu;
    protected Map<Integer, Menu> subMenus = new LinkedHashMap<>();
    private String name;


    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;

        if (menusHistory.isEmpty() || !this.equals(menusHistory.peek()))
            menusHistory.push(this);

    }

    public abstract void executeMenu();

    public void showMenu() {

        int size = subMenus.size();

        if (!subMenus.isEmpty()) {
            System.out.println("Menu : " + this.name);
        }

        for (Map.Entry<Integer, Menu> menuEntry : subMenus.entrySet()) {
            System.out.println(menuEntry.getKey() + ". " + menuEntry.getValue().getName());
        }

        if (parentMenu != null)
            System.out.println((size + 1) + ". Back");
        else {
            System.out.println((size + 1) + ". Exit");
        }

    }

    protected int getOption() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (Pattern.matches("[1-9][0-9]*", input))
                if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= subMenus.size() + 2)
                    return Integer.parseInt(input);
            System.out.println("Invalid Input : Please Enter A Valid Number");
        }
    }

    public String inputInFormatWithError(String message, String format, String error) {

        Pattern pattern = Pattern.compile(format);
        do {
            System.out.println(message);
            command = scanner.nextLine().trim();
            Matcher matcher = pattern.matcher(command);
            if (matcher.find())
                return command;
            if (!error.equals(""))
                System.err.println(error);
        } while (true);
    }

    public String inputInFormat(String message, String format) {
        return inputInFormatWithError(message, format, "");
    }

    public String getName() {
        return name;
    }

    public void back() {
        Menu lastMenu;

        do {
            lastMenu = menusHistory.pop();
        }
        while (lastMenu.equals(this));

        lastMenu.showMenu();
        lastMenu.executeMenu();
    }

    public void logout() {
        try {
            Controller.logout();
            System.out.println("You've logged out successfully !");
            Thread.sleep(1000);
            new MainMenu();
        } catch (HaveNotLoggedInException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
        }
    }

    public void login() {
        if (Controller.getCurrentAccount() != null)
            System.out.println("You've already logged in");
        else {
            var login = new LoginAndRegisterMenu(this);
            login.showMenu();
            login.executeMenu();
        }
    }


    public int getOptionWithRange(int from, int to) {
        String input;
        StringBuilder backAndLog = new StringBuilder("|(?i)back");
        if (Controller.getCurrentAccount() == null) {
            backAndLog.append("|(?i)login");
        } else {
            backAndLog.append("|(?i)logout");
        }
        String regex = "[1-9][0-9]*" + backAndLog.toString();
        while (true) {
            input = scanner.nextLine();
            if (Pattern.matches(regex, input)) {
                if (input.equalsIgnoreCase("back"))
                    return to + 1;
                else if (input.matches(backAndLog.substring(1)))
                    return to + 2;
                else if (Integer.parseInt(input) >= from && Integer.parseInt(input) <= to + 2)
                    return Integer.parseInt(input);
            }
            System.out.println("Invalid Input ! Please Enter A Valid Number : ");
        }
    }

    public double getOptionWithRangeDouble(double from, double to) {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (Pattern.matches("[1-9][0-9]*(\\.[0-9]+)?", input))
                if (Double.parseDouble(input) >= from && Double.parseDouble(input) <= to)
                    return Double.parseDouble(input);
            System.out.println("Invalid Input : Please Enter A Valid Number");
        }
    }
}
