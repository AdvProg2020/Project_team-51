package control.Exceptions;

public class OffCodeExpirationException extends Exception {
    public OffCodeExpirationException() {
        super("this offcode is expired");
    }
}
