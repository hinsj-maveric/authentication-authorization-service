package com.maveric.authenticationauthorizationservice.exception;

import com.maveric.authenticationauthorizationservice.dto.ErrorDto;
import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(UserNotFoundException e) {
        ErrorDto error = getError(e.getMessage(), String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorDto> handleFeignExceptionNotFound(FeignException e, HttpServletResponse response) {
        String message = e.contentUTF8();
        String decode = (String) e.contentUTF8().subSequence(message.lastIndexOf(":\""), message.length()-2);
        ErrorDto error = getError(decode.replace(":\"", ""), String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<ErrorDto> handleFeignExceptionBadRequest(FeignException e, HttpServletResponse response) {
        String message = e.contentUTF8();
        String decode = (String) e.contentUTF8().subSequence(message.lastIndexOf(":\""), message.length()-2);
        ErrorDto error = getError(decode.replace(":\"", ""), String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto>  handleInvalidInput(MethodArgumentNotValidException methodArgumentNotValidException){
        ErrorDto error = getError(methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage(),String.valueOf(HttpStatus.BAD_REQUEST));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto>  handleInvalidPasswordOrEmail(BadCredentialsException badCredentialsException){
        ErrorDto error = getError("Incorrect username/password" ,String.valueOf(HttpStatus.BAD_REQUEST));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    public ResponseEntity<ErrorDto>  serviceUnavailable(HttpServerErrorException.ServiceUnavailable serviceUnavailable){
        ErrorDto error = getError(String.valueOf(serviceUnavailable.getMessage()),String.valueOf(HttpStatus.SERVICE_UNAVAILABLE));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    private ErrorDto getError(String message , String code){
        ErrorDto error = new ErrorDto();
        error.setCode(code);
        error.setMessage(message);
        return error;
    }
}
