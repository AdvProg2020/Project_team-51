package control.Exceptions;

public class EmptyCartException extends Exception {
    public EmptyCartException() {
        super("cart is empty");
    }
}
