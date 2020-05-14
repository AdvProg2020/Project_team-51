package control.Exceptions;

public class AlreadyLoggedInException extends Exception {
    private String message;

    public AlreadyLoggedInException(String message) {
        super(message);
        this.message = message ;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
