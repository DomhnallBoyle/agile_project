package uk.ac.qub.csc3045.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseErrorException.class)
    public ResponseEntity<String> handleResponseError(ResponseErrorException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericError(RuntimeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}