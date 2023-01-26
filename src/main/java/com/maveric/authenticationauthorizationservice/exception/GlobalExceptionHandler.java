package com.maveric.authenticationauthorizationservice.exception;

import com.maveric.authenticationauthorizationservice.dto.ErrorDto;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorDto getError(String message , String code){
        ErrorDto error = new ErrorDto();
        error.setCode(code);
        error.setMessage(message);
        return error;
    }
}
