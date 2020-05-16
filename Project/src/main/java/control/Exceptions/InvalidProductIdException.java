package control.Exceptions;

public class InvalidProductIdException extends Exception{
    public InvalidProductIdException( ) {
        super("this product id is invalid");
    }
}
