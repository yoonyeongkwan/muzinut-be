package nuts.muzinut.exception.user;

public class AlreadyExistUser extends RuntimeException{

    public AlreadyExistUser() {
    }

    public AlreadyExistUser(String message) {
        super(message);
    }

    public AlreadyExistUser(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistUser(Throwable cause) {
        super(cause);
    }

    public AlreadyExistUser(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
