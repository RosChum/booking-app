package com.example.bookingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<ExceptionMessage> contentNotFoundExceptionHandler(ContentNotFoundException contentNotFoundException) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder().httpCode(HttpStatus.NO_CONTENT.name()).message(contentNotFoundException.getMessage()).build();
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exceptionMessage);
    }
}
