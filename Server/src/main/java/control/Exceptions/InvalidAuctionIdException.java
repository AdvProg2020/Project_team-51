package control.Exceptions;

public class InvalidAuctionIdException extends Exception {
    public InvalidAuctionIdException() {
        super("this auction is not valid");
    }
}
