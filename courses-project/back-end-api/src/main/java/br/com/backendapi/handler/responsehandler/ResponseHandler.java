package br.com.backendapi.handler.responsehandler;


import br.com.backendapi.exception.UserAlreadyExistsException;
import br.com.backendapi.exception.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseHandler {

    @ExceptionHandler(Validation.class)
    public ResponseEntity<Object> handleValidation(Validation ex) {
        return new ResponseEntity<>(ex.getValidations(), HttpStatus.valueOf(400));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(400));
    }

}
