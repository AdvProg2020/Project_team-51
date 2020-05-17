package control.Exceptions;

public class WrongFormatException extends Exception{
    public WrongFormatException(String message) {
        super("invalid format");
    }
}
