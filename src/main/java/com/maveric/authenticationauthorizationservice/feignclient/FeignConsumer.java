package com.maveric.authenticationauthorizationservice.feignclient;

import com.maveric.authenticationauthorizationservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service")
public interface FeignConsumer {

    @PostMapping("api/v1/users")
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);

    @GetMapping("api/v1/users/getUserByEmail/{emailId}")
    ResponseEntity<UserDto> getUserByEmail(@PathVariable String emailId);
}
