package com.example.musicBox.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(ErrorMessage errorMessage) {
        this.message = errorMessage.getMessage();
        this.httpStatus = errorMessage.getHttpStatus();
    }

    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


}
