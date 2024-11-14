package com.example.demo.exception;

import com.example.demo.model.dto.response.SimpleMessageResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<SimpleMessageResponse> handleResourceNotFoundException(@NonNull Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SimpleMessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    protected ResponseEntity<SimpleMessageResponse> handleNotEnoughFundsException(@NonNull Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SimpleMessageResponse(ex.getMessage()));
    }

}
