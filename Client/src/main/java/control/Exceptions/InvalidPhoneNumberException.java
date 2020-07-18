package control.Exceptions;

public class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException() {
        super("this phone number is invalid");
    }
}
