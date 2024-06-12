package nuts.muzinut.exception;

//이메일 인증 실패 예외
public class EmailVertFailEx extends RuntimeException{

    public EmailVertFailEx() {
        super();
    }

    public EmailVertFailEx(String message) {
        super(message);
    }

    public EmailVertFailEx(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailVertFailEx(Throwable cause) {
        super(cause);
    }

    protected EmailVertFailEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
