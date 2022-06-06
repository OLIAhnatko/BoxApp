package com.lviv.hnatko.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenToDoException extends RuntimeException{
    public ForbiddenToDoException() {
        super("You have no right to do so");
    }
}
