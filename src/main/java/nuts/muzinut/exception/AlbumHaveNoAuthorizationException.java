package nuts.muzinut.exception;

public class AlbumHaveNoAuthorizationException extends RuntimeException {
    public AlbumHaveNoAuthorizationException() {
    }

    public AlbumHaveNoAuthorizationException(String message) {
        super(message);
    }

    public AlbumHaveNoAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlbumHaveNoAuthorizationException(Throwable cause) {
        super(cause);
    }

    public AlbumHaveNoAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

