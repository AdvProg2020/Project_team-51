package message.Messages.ClientToServer;

import model.People.Account;

public class LogoutMessage {
    private Account account;

    public LogoutMessage(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
