package control.Exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("enterd password is invalid");
    }
}
