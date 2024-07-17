package nuts.muzinut.exception.chat;


//차단 당한 사용자가 제한된 행동을 할 때 예외 발생
public class BlockUserException extends RuntimeException{

    public BlockUserException() {
    }

    public BlockUserException(String message) {
        super(message);
    }

    public BlockUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockUserException(Throwable cause) {
        super(cause);
    }

    public BlockUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
