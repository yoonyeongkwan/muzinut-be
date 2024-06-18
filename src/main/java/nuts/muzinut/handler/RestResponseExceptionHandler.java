package nuts.muzinut.handler;

import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.dto.ErrorResult;
import nuts.muzinut.dto.ErrorDto;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({BoardNotExistException.class})
    private ErrorResult NO_CONTENT(BoardNotExistException ex) {
        return new ErrorResult(NO_CONTENT.value(), "no content!");
    }
}
