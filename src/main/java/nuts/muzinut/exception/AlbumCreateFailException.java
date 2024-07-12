package nuts.muzinut.exception;

public class AlbumCreateFailException extends RuntimeException{
    public AlbumCreateFailException() {
    }

    public AlbumCreateFailException(String message) {
        super(message);
    }

    public AlbumCreateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlbumCreateFailException(Throwable cause) {
        super(cause);
    }

    public AlbumCreateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
