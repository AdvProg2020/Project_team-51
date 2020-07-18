package control.Exceptions;

public class SameProductForComparisonException extends Exception {
    public SameProductForComparisonException() {
        super("you have to choose a different product to compare");
    }
}
