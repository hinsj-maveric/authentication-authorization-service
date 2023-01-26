package com.maveric.authenticationauthorizationservice.exception;

import com.maveric.authenticationauthorizationservice.dto.ErrorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
}
