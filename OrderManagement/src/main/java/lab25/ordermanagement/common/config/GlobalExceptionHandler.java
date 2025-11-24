package lab25.ordermanagement.common.config;

import lab25.ordermanagement.common.dto.ErrorResponseDTO;
import lab25.ordermanagement.common.exceptions.BadRequestDetailsException;
import jakarta.servlet.http.HttpServletRequest;
import lab25.ordermanagement.common.exceptions.ResourceNotFoundException;
import lab25.ordermanagement.common.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleValidationException(ValidationException cause) {
        return ErrorResponse.create(cause, HttpStatus.BAD_REQUEST, cause.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("IllegalArgumentException: {} at path {}", ex.getMessage(), request.getRequestURI());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleValidationException(RuntimeException cause) {
        log.error("RuntimeException occurred - handling with GlobalExceptionHandler: {}", cause.getMessage(), cause);
        return ErrorResponse.create(cause, HttpStatus.INTERNAL_SERVER_ERROR, cause.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponse handleResponseStatusException(ResponseStatusException cause) {
        return ErrorResponse.create(cause, cause.getStatusCode(), cause.getMessage());
    }

    @ExceptionHandler(BadRequestDetailsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomBadRequestException(BadRequestDetailsException ex, HttpServletRequest request) {
        log.warn("BadRequestDetailsException: {} at path {}", ex.getMessage(), request.getRequestURI());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            ResourceNotFoundException.class,
            NoSuchElementException.class,
    })
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(Exception ex, HttpServletRequest request) {
        log.error("ResourceNotFoundException: {} at path {}", ex.getMessage(), request.getRequestURI());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled Exception: {} at path {}", ex.getMessage(), request.getRequestURI(), ex);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
