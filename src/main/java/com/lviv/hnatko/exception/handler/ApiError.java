package com.lviv.hnatko.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiError {

    private HttpStatus error;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private String message;

    public ApiError(HttpStatus status, String message) {
        super();
        this.error = status;
        this.status = status.value();
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
}
