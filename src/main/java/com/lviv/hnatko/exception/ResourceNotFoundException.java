package com.lviv.hnatko.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resource;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resource, String fieldName, Object fieldValue) {
        super(String.format("%s with %s '%s' not found", resource, fieldName, fieldValue));
        this.resource = resource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public static Supplier<? extends ResourceNotFoundException> presentBoxSupplier(Integer presentBoxId) {
        return () -> new ResourceNotFoundException("presentBox", "id", presentBoxId);
    }
    public static Supplier<? extends ResourceNotFoundException> userSupplier(Integer userId) {
        return () -> new ResourceNotFoundException("user", "id", userId);
    }
    public static Supplier<? extends ResourceNotFoundException> presentOrderSupplier(Integer presentId) {
        return () -> new ResourceNotFoundException("presentId", "id", presentId);
    }
}
