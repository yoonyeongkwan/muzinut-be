package nuts.muzinut.exception.token;

public class UnsupportedTokenException extends RuntimeException{
    public UnsupportedTokenException() {
    }

    public UnsupportedTokenException(String message) {
        super(message);
    }

    public UnsupportedTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedTokenException(Throwable cause) {
        super(cause);
    }

    public UnsupportedTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
