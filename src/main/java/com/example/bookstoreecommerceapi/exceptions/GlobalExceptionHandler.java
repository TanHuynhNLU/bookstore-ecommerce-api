package com.example.bookstoreecommerceapi.exceptions;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseObject> handleBindException(BindException e) {
        StringBuilder errorMessages = new StringBuilder();
        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
            errorMessages.append(objectError.getDefaultMessage()).append(" ");
        }
        return ResponseEntity.badRequest().body(new ResponseObject(HttpStatus.BAD_REQUEST, errorMessages.toString(), null));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseObject> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        ResponseObject responseObject = new ResponseObject(HttpStatus.CONFLICT,e.getMessage(),null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObject);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseObject> handleUserNotFoundException(UserNotFoundException e){
        ResponseObject responseObject = new ResponseObject(HttpStatus.NOT_FOUND,e.getMessage(),null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }
}
