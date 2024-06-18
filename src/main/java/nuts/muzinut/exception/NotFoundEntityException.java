package nuts.muzinut.exception;

//데이터베이스로 부터 찾은 엔티티가 없을 때 발생하는 예외
public class NotFoundEntityException extends RuntimeException{

    public NotFoundEntityException() {
    }

    public NotFoundEntityException(String message) {
        super(message);
    }

    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundEntityException(Throwable cause) {
        super(cause);
    }

    public NotFoundEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
