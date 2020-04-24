package view;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public abstract class Menu {

    private static Scanner scanner = new Scanner(System.in);
    private static String command = "";
    private String name ;
    private Menu parentMenu ;
    protected HashMap<Integer,Menu> subMenus = new HashMap<Integer, Menu>();

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public void showMenu(){

    }

    public void executeMenu(){

    }

    public String inputInFormat(String input , Matcher matcher){
        return null;
    }
}
