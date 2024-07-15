package nuts.muzinut.exception.chat;

//채팅방 참여 요청은 한번만 보낼 수 있는데, 여러번 요청을 시도할 때 발생하는 예외
public class AlreadyExistRequestException extends RuntimeException{

    public AlreadyExistRequestException() {
    }

    public AlreadyExistRequestException(String message) {
        super(message);
    }

    public AlreadyExistRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistRequestException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
