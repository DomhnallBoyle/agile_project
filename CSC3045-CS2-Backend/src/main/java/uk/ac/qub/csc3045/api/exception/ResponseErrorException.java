package uk.ac.qub.csc3045.api.exception;

import org.springframework.http.HttpStatus;

public class ResponseErrorException extends RuntimeException {

    private HttpStatus statusCode;

    public ResponseErrorException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
