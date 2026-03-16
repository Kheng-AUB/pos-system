package com.kheng.pos.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {

    private final HttpStatus status;

    public UserException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND; // default
    }

    public UserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public UserException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

}
