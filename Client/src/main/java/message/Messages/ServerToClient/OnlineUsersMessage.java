package message.Messages.ServerToClient;

import model.People.Account;

import java.util.LinkedList;
import java.util.List;

public class OnlineUsersMessage {
    List<Account> accounts = new LinkedList<>();

    public OnlineUsersMessage(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
