package com.maveric.authenticationauthorizationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.authenticationauthorizationservice.constant.Gender;
import com.maveric.authenticationauthorizationservice.dto.AuthRequest;
import com.maveric.authenticationauthorizationservice.dto.UserDto;
import com.maveric.authenticationauthorizationservice.feignclient.FeignConsumer;
import com.maveric.authenticationauthorizationservice.service.JWTService;
import com.maveric.authenticationauthorizationservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class AuthControllerTest {

    private static final String API_V1_AUTH = "http://localhost:3000/api/v1/auth";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private Authentication authentication;

    @MockBean
    private FeignConsumer feignConsumer;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signup() throws Exception {
        when(feignConsumer.createUser(any(UserDto.class))).thenReturn(getSampleUserDto());
        when(jwtService.generateToken(any(UserDto.class))).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWphNjNAZ21haWwuY29tIiwiZXhwIjoxNjYzNjkyNzQ3LCJpYXQiOjE2NjM2NTY3N");
        mockMvc.perform(post(API_V1_AUTH+"/signup").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSampleUserDto())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void login() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(feignConsumer.getUserByEmail(any())).thenReturn(getSampleUserDto());
        mockMvc.perform(post(API_V1_AUTH+"/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSampleLoginUserDto())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAuthResponse() throws Exception{
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NJ9.eyJzdWIiOiJyYWphQGdtYWlsLmNvbSIsImV4cCI6MTY2MzI1NzA5MCwiaWF0IjoxNjYzMjIxMDkwfQ.wQl4ssfFUiRtqpsbVYGtFT1kS7MFMI6PwSJc3K5Jw2M";
        mockMvc.perform(get(API_V1_AUTH+"/validateToken").
                        header("Authorization", bearerToken)).
                andExpect(status().isOk())
                .andDo(print());
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

    public ResponseEntity<AuthRequest> getSampleLoginUserDto(){
        AuthRequest user = new AuthRequest();
        user.setEmail("hinsj@maveric-systems.com");
        user.setPassword("Pass@word11");

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}