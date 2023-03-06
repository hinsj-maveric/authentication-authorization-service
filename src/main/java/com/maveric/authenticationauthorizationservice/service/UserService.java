package com.maveric.authenticationauthorizationservice.service;

import com.maveric.authenticationauthorizationservice.dto.UserDto;
import com.maveric.authenticationauthorizationservice.exception.UserNotFoundException;
import com.maveric.authenticationauthorizationservice.feignclient.FeignConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    FeignConsumer feignConsumer;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ResponseEntity<UserDto> userResponseEntity = feignConsumer.getUserByEmail(email);
        if(userResponseEntity.getBody() != null){
            UserDto user = userResponseEntity.getBody(); //NOSONAR
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()); //NOSONAR
        }else {
            throw new UserNotFoundException("User not found with the email " + email);
        }
    }
}
