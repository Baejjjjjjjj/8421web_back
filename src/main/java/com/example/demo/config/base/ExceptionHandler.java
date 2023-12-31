package com.example.demo.config.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    // Exception Handler
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e, WebRequest request) {
        log.error(">>> Exception");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(Code.SERVER_ERROR));
    }

    // BaseException Handler
    @org.springframework.web.bind.annotation.ExceptionHandler(value = BaseException.class)
    public ResponseEntity baseException(BaseException e, WebRequest request) {
        log.error(">>> BaseException - " + e.getCode().getMessage() + "(" + e.getCode().getCode() + ")");
        e.printStackTrace();
        return ResponseEntity.status(e.getCode().getHttpStatus()).body(new BaseResponse(e.getCode()));
    }

    // Validation Exception Handler
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request){
        String message = ex.getAllErrors().get(0).getDefaultMessage();
        log.error(">>> MethodArgumentNotValid - " + message);
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(Code.REQUEST_ERROR, message));
    }

    // Internal Exception Handler
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(">>> Internal Exception");
        ex.printStackTrace();
        return ResponseEntity.status(status).body(new BaseResponse(Code.INTERNAL_SERVER_ERROR, status.getReasonPhrase()));
    }
}
