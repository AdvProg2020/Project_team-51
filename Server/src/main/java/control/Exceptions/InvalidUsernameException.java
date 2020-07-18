package control.Exceptions;

public class InvalidUsernameException extends Exception {
    public InvalidUsernameException() {
        super("this username is not valid");
    }
}
