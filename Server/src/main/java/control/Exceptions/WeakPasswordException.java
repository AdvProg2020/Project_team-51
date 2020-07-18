package control.Exceptions;

public class WeakPasswordException extends Exception {
    public WeakPasswordException() {
        super("password must be at least 4 charcters long");
    }
}
