package com.maveric.authenticationauthorizationservice.exception;

import com.maveric.authenticationauthorizationservice.dto.ErrorDto;
import com.maveric.authenticationauthorizationservice.feignclient.FeignConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticationException {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleInValidPasswordOrEmail(){
        BadCredentialsException badCredentialsException = new BadCredentialsException("Incorrect Email/Password");
        ResponseEntity<ErrorDto> errorDtoResponseEntity = globalExceptionHandler.handleInvalidPasswordOrEmail(badCredentialsException);

        assertEquals("400 BAD_REQUEST", errorDtoResponseEntity.getBody().getCode());
    }

    @Test
    void handleUserNotFound() {
        UserNotFoundException exception = new UserNotFoundException("User not found");
        ResponseEntity<ErrorDto> error = globalExceptionHandler.handleUserNotFound(exception);
        assertEquals("404", error.getBody().getCode());
    }
}
