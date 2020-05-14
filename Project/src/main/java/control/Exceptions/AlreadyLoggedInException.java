package control.Exceptions;

public class AlreadyLoggedInException extends Exception {
    public AlreadyLoggedInException(String message) {
        super(message);
    }
}
