package by.bsuir.app.exception;

public class GettingDataException extends Exception{
    public GettingDataException() {
        super();
    }

    public GettingDataException(String message) {
        super(message);
    }

    public GettingDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public GettingDataException(Throwable cause) {
        super(cause);
    }
}
