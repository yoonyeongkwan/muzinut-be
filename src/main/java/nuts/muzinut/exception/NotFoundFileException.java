package nuts.muzinut.exception;

public class NotFoundFileException extends RuntimeException{

    public NotFoundFileException() {
    }

    public NotFoundFileException(String message) {
        super(message);
    }

    public NotFoundFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundFileException(Throwable cause) {
        super(cause);
    }

    public NotFoundFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
