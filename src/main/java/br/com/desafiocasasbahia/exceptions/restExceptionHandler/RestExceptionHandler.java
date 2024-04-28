package br.com.desafiocasasbahia.exceptions.restExceptionHandler;

import br.com.desafiocasasbahia.exceptions.InvalidDocumentException;
import br.com.desafiocasasbahia.exceptions.UserAlreadyExistsException;
import br.com.desafiocasasbahia.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.DateTimeException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestExceptionHandlerResponse> userNotFoundHandler (
            UserNotFoundException exception) {
        RestExceptionHandlerResponse treatedException = new RestExceptionHandlerResponse(
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatedException);
    }

    @ExceptionHandler(InvalidDocumentException.class)
    private ResponseEntity<RestExceptionHandlerResponse> invalidDocumentHandler (
            InvalidDocumentException exception) {
        RestExceptionHandlerResponse treatedException = new RestExceptionHandlerResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(treatedException);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<RestExceptionHandlerResponse> userAlreadyExistsHandler(
            UserAlreadyExistsException exception) {
        RestExceptionHandlerResponse treatedException = new RestExceptionHandlerResponse(
                HttpStatus.CONFLICT.name(),
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(treatedException);
    }

    @ExceptionHandler(DateTimeException.class)
    private ResponseEntity<RestExceptionHandlerResponse> dateTimeHandler(
            DateTimeException exception) {
        RestExceptionHandlerResponse treatedException = new RestExceptionHandlerResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(treatedException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<RestExceptionHandlerResponse> invalidEmailHandler(
            MethodArgumentNotValidException exception) {
        RestExceptionHandlerResponse treatedException = new RestExceptionHandlerResponse(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatedException);
    }
}
