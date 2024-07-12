package nuts.muzinut.exception;

public class LimitPlayNutException extends RuntimeException{
    public LimitPlayNutException() {
    }

    public LimitPlayNutException(String message) {
        super(message);
    }

    public LimitPlayNutException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitPlayNutException(Throwable cause) {
        super(cause);
    }

    public LimitPlayNutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
