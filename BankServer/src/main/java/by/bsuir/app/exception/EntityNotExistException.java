package by.bsuir.app.exception;

public class EntityNotExistException extends Exception{
    private static final String msg = "Account doesn't exist, id: ";
    
    public EntityNotExistException() {
        super();
    }

    public EntityNotExistException(String message) {
        super(msg + message);
    }

    public EntityNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotExistException(Throwable cause) {
        super(cause);
    }
}
