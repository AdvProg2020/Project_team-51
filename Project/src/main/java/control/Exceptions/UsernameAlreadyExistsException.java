package control.Exceptions;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException() {
        super("this username is taken");
    }
}
