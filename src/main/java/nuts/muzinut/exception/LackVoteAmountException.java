package nuts.muzinut.exception;

public class LackVoteAmountException extends RuntimeException{
    public LackVoteAmountException() {
    }

    public LackVoteAmountException(String message) {
        super(message);
    }

    public LackVoteAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public LackVoteAmountException(Throwable cause) {
        super(cause);
    }

    public LackVoteAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
