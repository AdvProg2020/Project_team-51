package control.Exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("you dont have enough money");
    }
}
