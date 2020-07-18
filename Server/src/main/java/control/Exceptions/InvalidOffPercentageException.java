package control.Exceptions;

public class InvalidOffPercentageException extends Exception {
    public InvalidOffPercentageException() {
        super("please enter an integer from 1 to 100 as off percentage");
    }
}
