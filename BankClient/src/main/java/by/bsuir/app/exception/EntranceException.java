package by.bsuir.app.exception;

public class EntranceException extends Exception{
    public EntranceException() {
        super();
    }

    public EntranceException(String message) {
        super(message);
    }

    public EntranceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntranceException(Throwable cause) {
        super(cause);
    }
}
