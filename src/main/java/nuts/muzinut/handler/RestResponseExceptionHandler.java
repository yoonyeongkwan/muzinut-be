package nuts.muzinut.handler;

import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.dto.ErrorResult;
import nuts.muzinut.dto.ErrorDto;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.exception.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Order(1)
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
    @ExceptionHandler(value = { NotFoundMemberException.class, AccessDeniedException.class, InvalidPasswordException.class })
    @ResponseBody
    private ErrorDto forbidden(RuntimeException ex, WebRequest request) {
        return new ErrorDto(FORBIDDEN.value(), ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = { EmailVertFailException.class, NotFoundEntityException.class,
            BoardNotFoundException.class, NoUploadFileException.class })
    @ResponseBody
    private ErrorDto BAD_REQUEST(RuntimeException ex, WebRequest request){
        log.info("BoardNotFoundException 호출");
        return new ErrorDto(BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(NO_CONTENT)
    @ExceptionHandler(value = {BoardNotExistException.class, NotFoundFileException.class})
    @ResponseBody
    private ErrorDto NO_CONTENT(RuntimeException ex, WebRequest request) {
        log.info("NotFoundFileException 호출");
        return new ErrorDto(NO_CONTENT.value(), ex.getMessage());
    }

    // 새로운 예외 핸들러 추가
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = { IllegalArgumentException.class })
    @ResponseBody
    private ErrorDto handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ErrorDto(BAD_REQUEST.value(), ex.getMessage());
    }

    // 앨범 Entity 생성 실패 핸들러 추가
    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(value = { AlbumCreateFailException.class })
    @ResponseBody
    private ErrorDto INTERNAL_SERVER_ERROR(AlbumCreateFailException ex, WebRequest request) {
        return new ErrorDto(SERVICE_UNAVAILABLE.value(), ex.getMessage());
    }
}
