package control.Exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Wrong password ! ");
    }
}
