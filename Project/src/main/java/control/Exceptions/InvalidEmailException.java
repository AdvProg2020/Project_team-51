package control.Exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException( ) {
        super("this email is not vaild");
    }
}
