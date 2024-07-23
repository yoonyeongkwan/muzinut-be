package nuts.muzinut.exception.board;

public class BoardSearchTypeNotExistException extends RuntimeException{

    public BoardSearchTypeNotExistException() {
    }

    public BoardSearchTypeNotExistException(String message) {
        super(message);
    }

    public BoardSearchTypeNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardSearchTypeNotExistException(Throwable cause) {
        super(cause);
    }

    public BoardSearchTypeNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
