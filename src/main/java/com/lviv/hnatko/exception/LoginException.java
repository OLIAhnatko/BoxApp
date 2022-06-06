package com.lviv.hnatko.exception;


import com.lviv.hnatko.entity.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginException extends RuntimeException implements Supplier<AppUser> {

    public LoginException() {
        super("Invalid email or password");
    }

    public LoginException(String message) {
        super(message);
    }

    public static Supplier<? extends LoginException> badLogin() {
        return () -> new LoginException("Invalid email or password");
    }

    @Override
    public AppUser get() {
        return null;
    }
}
