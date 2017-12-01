package uk.ac.qub.csc3045.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseErrorExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles the response error exception by returning
	 * it's details inside a ResponseEntity
	 * @param ex response error exception
	 * @return response entity with exception details
	 */
    @ExceptionHandler(ResponseErrorException.class)
    public ResponseEntity<String> handleResponseError(ResponseErrorException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    /**
     * Handles a more generic status code 500 internal server error
     * @param ex - runtime exception
     * @return response entity with exception details and status codde 500
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericError(RuntimeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
