package uk.ac.qub.csc3045.api.exception;

import org.springframework.http.HttpStatus;

public class ResponseErrorException extends RuntimeException {

	/**
	 * Private variables
	 */
    private HttpStatus statusCode;

    /**
     * Constructor for ResponseErrorException
     * @param message string of error exception
     * @param statusCode status code of the exception
     */
    public ResponseErrorException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Returns the status code of the response error exception
     * @return status code
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
