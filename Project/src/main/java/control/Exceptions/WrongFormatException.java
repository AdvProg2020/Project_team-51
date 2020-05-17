package control.Exceptions;

public class WrongFormatException extends Exception{
    public WrongFormatException(String message) {
        super("please use the Right "+message+ " format");
    }
}

