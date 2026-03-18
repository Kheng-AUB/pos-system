package com.kheng.pos.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        log.warn("AppException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(),
                "fail",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
        log.warn("UserException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatus().value() == 404 ? "404" : "1",
                "fail",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Internal server error", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "1",
                "fail",
                "Internal server error"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    public static class ErrorResponse {
        private final String code;
        private final String status;
        private final String msg;

        public ErrorResponse(String code, String status, String msg) {
            this.code = code;
            this.status = status;
            this.msg = msg;
        }

    }
}
