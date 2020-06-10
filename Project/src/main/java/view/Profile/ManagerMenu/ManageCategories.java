package view.Profile.ManagerMenu;

import control.Controller;
import control.ManagerController;
import model.Attributes;
import model.Category;
import model.People.Account;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

import java.util.ArrayList;

public class ManageCategories extends Menu {
    ManagerController managerController;

    public ManageCategories(Menu parentMenu) {
        super("manage categories", parentMenu);
        subMenus.put(1, new Menu("edit category menu" , this) {
            @Override
            public void executeMenu() {
                editCategory();
            }
        });
        subMenus.put(2, new Menu("add category menu" , this) {
            @Override
            public void executeMenu() {
                addCategory();
            }
        });

        subMenus.put(3, new Menu("remove category menu" , this) {
            @Override
            public void executeMenu() {
                removeCategory();
            }
        });
    }

    void editCategory(){
        System.out.println("please enter category id to edit");
        String categoryId;
        while (true){
            categoryId = scanner.nextLine();
            if (!managerController.getCategoryById(categoryId).equals(null)) break;
            System.err.println("please enter correct category id");
        }
        Category category = managerController.getCategoryById(categoryId);
        System.out.println("select action" +
                "\n" +
                "1. add product\n" +
                "2. remove product\n" +
                "3. edit name\n" +
                "4. add attribute\n" +
                "5. remove attribute\n" +
                "6. remove child category\n" +
                "7. add child category\n" +
                "8. back");
        int option = Integer.parseInt(inputInFormat("" , "^[1-8]$"));
        switch (option){
            case 1:{
                editCategoryAddProduct(category);
            break;
            }case 2:{
                editCategoryRemoveProduct(category);
                break;
            }case 3:{
                editCategoryName(category);
                break;
            }case 4:{
                editCategoryAddAttribute();
                break;
            }case 5:{
                editCategoryRemoveAttribute();
                break;
            }case 6:{
                editCategoryRemoveChildCategory(category);
                break;
            }case 7:{
                editCategoryAddChildCategory(category);
                break;
            }case 8:{
                return;
            }
        }
    }

    void editCategoryAddProduct(Category category){
        String id = inputInFormat("plese enter productId" , "^\\w+$");
        try {
            managerController.addProductToCategory(category.getCategoryId() , id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    void editCategoryRemoveProduct(Category category){
        String id = inputInFormat("plese enter productId" , "^\\w+$");
        try {
            managerController.addProductToCategory(category.getCategoryId() , id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    void editCategoryName (Category category){
        String newName = inputInFormat("please enter new name" , "^\\w+$");
        try {
            managerController.editCategoryName(category.getName() , newName);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    void editCategoryAddAttribute(){
        //todo i really dont have an idea about attributes
    }

    void editCategoryRemoveAttribute(){
        //todo i really dont have an idea about attributes
    }

    void editCategoryAddChildCategory(Category category){
        String childCategoryName = inputInFormat("please enter child category name" , "^\\w+$");
        try {
            managerController.addChildCategory(category.getName() , childCategoryName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    void editCategoryRemoveChildCategory(Category category){
        String childCategoryName = inputInFormat("please enter child category name" , "^\\w+$");
        try {
            managerController.removeChildCategory(category.getName() , childCategoryName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    void addCategory(){
        String name = inputInFormat("please enter category name " , "^\\w+$");
        String parentName = inputInFormat("please enter parent category name" , "^\\w+$");
        System.out.println("please enter attribute names for category. enter @ to end");
        ArrayList<String > attributeNames = new ArrayList<>();
        ArrayList <Attributes> attributes = new ArrayList<>();
        String attributeName;
        while (!(attributeName = MainMenu.scanner.nextLine()).equals("@")){
            try {
                Attributes a = managerController.getAttributeById(attributeName);
                attributes.add(a);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        managerController.addCategory(name , parentName , attributes.toArray(new Attributes[attributes.size()]));
    }

    void removeCategory(){
        System.out.println("please enter category name");
        String name = scanner.nextLine();
        try{managerController.removeCategory(name);}catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void executeMenu() {
        menusHistory.push(this);
        int size = subMenus.size();
        int option = getOptionWithRange(1, size);

        if (option <= size) {
            var nextMenu = subMenus.get(option);
            nextMenu.showMenu();
            nextMenu.executeMenu();
        } else if (option == size + 1) {
            back();
        } else if (option == size + 2) {
            if (Controller.getCurrentAccount() == null) {
                var login = new LoginMenu(this);
                login.showMenu();
                login.executeMenu();
            } else {
                logout();
                var mainMenu = new MainMenu();
                mainMenu.showMenu();
                mainMenu.executeMenu();
            }
        }
        this.executeMenu();
    }
}
