package com.maveric.authenticationauthorizationservice.feignclient;

import com.maveric.authenticationauthorizationservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feignUser", url = "http://localhost:3005/api/v1")
public interface FeignConsumer {

    @PostMapping("/users")
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);

    @GetMapping("/users/getUserByEmail/{emailId}")
    ResponseEntity<UserDto> getUserByEmail(@PathVariable String emailId);
}
