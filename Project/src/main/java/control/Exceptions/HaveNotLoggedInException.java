package control.Exceptions;

public class HaveNotLoggedInException extends Exception {
    public HaveNotLoggedInException() {
        super("you are not logged in");
    }
}
