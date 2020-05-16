package control.Exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("password is incorrect");
    }
}
