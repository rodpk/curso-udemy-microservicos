package br.com.rodpk.productapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // intecptador de exceções
public class ExceptionGlobalHandler {
    

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        var details = new ExceptionDetails();
        details.setStatus(HttpStatus.BAD_REQUEST.value());
        details.setMessage(e.getMessage());
        //ResponseEntity<?> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }


    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException e) {
        var details = new ExceptionDetails();
        details.setStatus(HttpStatus.UNAUTHORIZED.value());
        details.setMessage(e.getMessage());
        //ResponseEntity<?> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(details);
    }
}
