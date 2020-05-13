package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {

    private static Stack<Menu> menusHistory = new Stack<>();
    public static final Scanner scanner = new Scanner(System.in);
    private static String command = "";
    private String name ;
    private Menu parentMenu ;
    protected HashMap<Integer,Menu> subMenus = new HashMap<Integer, Menu>();


    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public abstract void executeMenu();

    public void showMenu(){

        int size = subMenus.size();

        if (!subMenus.isEmpty()){
            System.out.println("Menu : " + this.name);
        }

        for (Map.Entry<Integer, Menu> menuEntry : subMenus.entrySet()) {
            System.out.println(menuEntry.getKey() + ". " + menuEntry.getValue().getName());
        }

        if (parentMenu != null)
            System.out.println((size+1) + ". Back");
        else {
            System.out.println((size+1) + ". Exit");
        }

    }

    protected int getOption() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (Pattern.matches("[1-9][0-9]*", input))
                if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= subMenus.size()+1)
                    return Integer.parseInt(input);
            System.out.println("Invalid Input : Please Enter A Valid Number");
        }
    }

    public String inputInFormatWithError(String message , String format , String error){
        Pattern pattern = Pattern.compile(format);
        do {
            command = scanner.nextLine().trim();
            Matcher matcher = pattern.matcher(command);
            if (matcher.find())
                return command;
            if (!error.equals(""))
            System.out.println(error);
            System.out.println(message);
        } while (true);
    }

    public String inputInFormat(String message , String format){
        return inputInFormatWithError(message,format,"");
    }

    public String getName() {
        return name;
    }
}
