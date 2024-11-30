package br.com.courses.handler.responsehandler;


import br.com.courses.exception.Validation;
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

}
