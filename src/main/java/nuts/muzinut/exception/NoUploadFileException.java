package nuts.muzinut.exception;

public class NoUploadFileException extends Exception{

    public NoUploadFileException() {
    }

    public NoUploadFileException(String message) {
        super(message);
    }

    public NoUploadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUploadFileException(Throwable cause) {
        super(cause);
    }

    public NoUploadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
