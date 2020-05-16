package control.Exceptions;

public class WrongFormatException extends Exception{
    public WrongFormatException() {
        super("invalid format");
    }
}
