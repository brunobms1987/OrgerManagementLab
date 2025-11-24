package lab25.ordermanagement.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestDetailsException extends RuntimeException {
    public BadRequestDetailsException(String message) {
        super(message);
    }

    public BadRequestDetailsException(String message, Throwable cause) {
        super(message, cause);
    }
}
