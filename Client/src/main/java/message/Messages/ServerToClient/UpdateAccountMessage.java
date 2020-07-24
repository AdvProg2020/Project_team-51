package message.Messages.ServerToClient;

import model.People.Account;

public class UpdateAccountMessage {

    private Account account;

    public UpdateAccountMessage(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
