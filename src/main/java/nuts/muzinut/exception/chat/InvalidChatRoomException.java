package nuts.muzinut.exception.chat;

//채팅방이 유효하지 않는 예외 (채팅방 존재하지 않음, 채팅방 얼려짐.. 등)
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
