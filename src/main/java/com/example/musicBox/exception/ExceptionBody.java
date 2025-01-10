package com.example.musicBox.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ExceptionBody {


    private String errorMessage;
    private HttpStatus httpStatus;
    private String methodName;
    private String time;

}
