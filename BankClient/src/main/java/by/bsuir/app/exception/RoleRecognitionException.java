package by.bsuir.app.exception;

public class RoleRecognitionException extends Exception{
    public RoleRecognitionException() {
        super();
    }

    public RoleRecognitionException(String message) {
        super(message);
    }

    public RoleRecognitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleRecognitionException(Throwable cause) {
        super(cause);
    }
}
