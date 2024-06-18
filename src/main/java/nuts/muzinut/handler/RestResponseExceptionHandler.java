package nuts.muzinut.handler;

import nuts.muzinut.dto.ErrorResult;
import nuts.muzinut.dto.ErrorDto;
import nuts.muzinut.exception.DuplicateMemberException;
import nuts.muzinut.exception.EmailVertFailException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    //이미 존재하는 회원이 회원가입을 시도할 때 발생한다
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = { DuplicateMemberException.class })
    @ResponseBody
    private ErrorDto conflict(RuntimeException ex, WebRequest request) {
        return new ErrorDto(CONFLICT.value(), ex.getMessage());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = { NotFoundMemberException.class, AccessDeniedException.class })
    @ResponseBody
    private ErrorDto forbidden(RuntimeException ex, WebRequest request) {
        return new ErrorDto(FORBIDDEN.value(), ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = { EmailVertFailException.class, NotFoundEntityException.class })
    @ResponseBody
    private ErrorDto BAD_REQUEST(EmailVertFailException ex, WebRequest request){
        return new ErrorDto(FORBIDDEN.value(), ex.getMessage());
    }
}
