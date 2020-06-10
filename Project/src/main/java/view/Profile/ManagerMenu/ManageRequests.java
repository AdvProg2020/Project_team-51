package view.Profile.ManagerMenu;

import control.Controller;
import control.ManagerController;
import model.Requests.Request;
import view.LoginMenu;
import view.MainMenu;
import view.Menu;

public class ManageRequests extends Menu {
    ManagerController managerController;

    public ManageRequests(Menu parentMenu , ManagerController mc) {
        super("manage requests", parentMenu);
        managerController = mc;
        subMenus.put(1, new Menu("show all requests" , this) {
            @Override
            public void executeMenu() {
                showAllRequests();
            }
        });
        subMenus.put(2, new Menu("answer request" , this) {
            @Override
            public void executeMenu() {
                answerRequest();
            }
        });
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

    private void showAllRequests(){
        for (Request r : managerController.getAllRequests()) System.out.println(r);
    }

    private void answerRequest() {
        Request r;
//        var viewRequestMenu = new Menu("view Request Menu", this) {
//
//            @Override
//            public void showMenu() {
//                System.out.println("1. accept");
//                System.out.println("2. reject");
//                System.out.println("3. back");
//            }
//
//            @Override
//            public void executeMenu() {
//                menusHistory.push(this);
//                command = inputInFormat("", "^[1-3]$");
//                if (command.equals("1")) acceptRequest(r.getRequestId());
//                if (command.equals("2")) declineRequest(r.getRequestId());
//                if (command.equals("3")) back();
//            }
//        };
//
//        String id = inputInFormat("enter request id", "\\w+");
//        try {
//            r = managerController.getRequestById(id);
//            System.out.print(r.getRequestId() + "  :  ");
//            System.out.println(r.digest());
//            viewRequestMenu.showMenu();
//            viewRequestMenu.executeMenu();
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
        try {
            r = managerController.getRequestById(inputInFormat("enter request id" ,
                    ""));
            System.out.println("1.accept\n2.decline\n3.back");
            int answer = getOptionWithRange(1 , 3);
            switch (answer){
                case 1 : {
                    acceptRequest(r);
                } case 2 : {
                    declineRequest(r);
                    break;
                } case 3 :{
                    return;
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void acceptRequest(Request r) {
        try { managerController.acceptRequest(r.getRequestId());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void declineRequest(Request r) {
        try {
            managerController.rejectRequest(r.getRequestId());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}


