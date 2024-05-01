package com.example.bookingapp.exception;

import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<ExceptionMessage> contentNotFoundExceptionHandler(ContentNotFoundException contentNotFoundException) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder().httpCode(HttpStatus.NO_CONTENT.name()).message(contentNotFoundException.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exceptionMessage);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> validFieldDtoException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ExceptionMessage exceptionMessage = ExceptionMessage.builder().message(String.join(" , ", errorMessage)).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);

    }


    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<ExceptionMessage> userFoundException(UserFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionMessage.builder().message(exception.getMessage()).build());

    }

    @ExceptionHandler({UserAlreadyExistException.class, BookingDatesException.class})
    public ResponseEntity<ExceptionMessage> userAlreadyExistException(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionMessage.builder().message(exception.getMessage()).build());
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionMessage> authenticationException(AuthenticationException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionMessage.builder().message(exception.getMessage()).build());

    }
}
