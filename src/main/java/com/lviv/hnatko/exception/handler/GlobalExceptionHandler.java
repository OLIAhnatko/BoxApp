package com.lviv.hnatko.exception.handler;

import com.lviv.hnatko.exception.ForbiddenToDoException;
import com.lviv.hnatko.exception.LoginException;
import com.lviv.hnatko.exception.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({ForbiddenToDoException.class})
    public ResponseEntity<ApiError> handleForbiddenToDoSomething(ForbiddenToDoException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({LoginException.class})
    public ResponseEntity<ApiError> handleLoginException(LoginException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, null, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public HttpEntity<String> handleInvalidDateFormatException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
