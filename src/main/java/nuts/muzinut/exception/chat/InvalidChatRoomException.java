package nuts.muzinut.exception.chat;

public class InvalidChatRoomException extends RuntimeException{

    public InvalidChatRoomException() {
    }

    public InvalidChatRoomException(String message) {
        super(message);
    }

    public InvalidChatRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidChatRoomException(Throwable cause) {
        super(cause);
    }

    public InvalidChatRoomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
