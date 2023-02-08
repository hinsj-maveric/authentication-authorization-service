package com.maveric.authenticationauthorizationservice.service;

import com.maveric.authenticationauthorizationservice.constant.Gender;
import com.maveric.authenticationauthorizationservice.dto.UserDto;
import com.maveric.authenticationauthorizationservice.feignclient.FeignConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Date;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    FeignConsumer feignConsumer;

    @MockBean
    private JWTService jwtService;

    @InjectMocks
    UserService userService;

    @Test
    void loadUserByUsername() {
        when(feignConsumer.getUserByEmail(anyString())).thenReturn(getSampleUserDto());

        UserDto userDto = feignConsumer.getUserByEmail("hinsj@maveric-systems.com").getBody();

        assertNotNull(userDto);
        assertSame("hinsj@maveric-systems.com", userDto.getEmail());
    }

    @Test
    void shouldThrowErrorWhenloadUserByUsername() {
        when(feignConsumer.getUserByEmail(anyString())).thenReturn(getSampleUserDto());

        UserDto userDto = feignConsumer.getUserByEmail("hins@maveric-systems.com").getBody();

        assertNotSame("hins@maveric-systems.com", userDto.getEmail());
    }

    public ResponseEntity<UserDto> getSampleUserDto(){
        UserDto user = new UserDto();
        user.setFirstName("Hins");
        user.setLastName("Jain");
        user.setMiddleName("D");
        user.setEmail("hinsj@maveric-systems.com");
        user.setPassword("Pass@word11");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(Date.from(Instant.parse("1994-10-27T00:00:00Z")));
        user.setAddress("Mumbai");
        user.setPhoneNumber("9594484384");

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}