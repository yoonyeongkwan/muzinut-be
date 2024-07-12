package nuts.muzinut.exception;

public class EntityOversizeException extends RuntimeException{
    public EntityOversizeException() {
    }

    public EntityOversizeException(String message) {
        super(message);
    }

    public EntityOversizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityOversizeException(Throwable cause) {
        super(cause);
    }

    public EntityOversizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
