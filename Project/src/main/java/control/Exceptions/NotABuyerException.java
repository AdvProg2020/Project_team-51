package control.Exceptions;

public class NotABuyerException extends Exception {
    public NotABuyerException() {
        super("only customers can buy");
    }
}
