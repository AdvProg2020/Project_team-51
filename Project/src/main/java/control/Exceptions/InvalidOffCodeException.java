package control.Exceptions;

public class InvalidOffCodeException extends Exception{
    public InvalidOffCodeException() {
        super("this off code is invalid");
    }
}
