package message.requests;

public class loginMessage {

    private String username;
    private String password;

    public loginMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
