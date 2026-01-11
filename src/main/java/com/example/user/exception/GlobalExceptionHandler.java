package com.example.user.exception;

import com.example.user.constant.StatusCodes;
import com.example.user.response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Status> handleUserNotFound(UserNotFoundException ex) {

        Status status = new Status(
                StatusCodes.USER_NOT_FOUND,
                ex.getMessage()
        );

        return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
    }
}
