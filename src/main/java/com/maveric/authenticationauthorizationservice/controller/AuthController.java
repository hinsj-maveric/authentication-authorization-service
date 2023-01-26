package com.maveric.authenticationauthorizationservice.controller;

import com.maveric.authenticationauthorizationservice.dto.AuthRequest;
import com.maveric.authenticationauthorizationservice.dto.AuthenticationResponse;
import com.maveric.authenticationauthorizationservice.dto.UserDto;
import com.maveric.authenticationauthorizationservice.feignclient.FeignConsumer;
import com.maveric.authenticationauthorizationservice.service.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    FeignConsumer feignConsumer;

    @Autowired
    JWTService jwtService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthenticationResponse> signup(@Valid @RequestBody UserDto userDto){
        ResponseEntity<UserDto> userResponseEntity = feignConsumer.createUser(userDto);
        final String jwt = jwtService.generateToken(userResponseEntity.getBody());

        return new ResponseEntity<>(getAuthResponse(jwt, userResponseEntity.getBody()), HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthRequest authRequest){
        UserDto user = null;

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                authRequest.getPassword()));

        ResponseEntity<UserDto> userResponseEntity = feignConsumer.getUserByEmail(authRequest.getEmail());
        user = userResponseEntity.getBody();

        final String jwt = jwtService.generateToken(user);

        return new ResponseEntity<>(getAuthResponse(jwt, user), HttpStatus.OK);
    }


    public AuthenticationResponse getAuthResponse(String token , UserDto user){
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setUser(user);
        authResponse.setToken(token);

        return authResponse;
    }
}
