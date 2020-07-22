package message.Messages.ServerToClient;

public class ExceptionMessage {

    private String exceptionString;

    public ExceptionMessage(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    public String getExceptionString() {
        return exceptionString;
    }
}
