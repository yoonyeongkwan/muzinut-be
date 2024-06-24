package nuts.muzinut.exception;

//게시판이 존재하지 않는 예외
public class BoardNotExistException extends Exception{

    public BoardNotExistException() {
    }

    public BoardNotExistException(String message) {
        super(message);
    }

    public BoardNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardNotExistException(Throwable cause) {
        super(cause);
    }

    public BoardNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
