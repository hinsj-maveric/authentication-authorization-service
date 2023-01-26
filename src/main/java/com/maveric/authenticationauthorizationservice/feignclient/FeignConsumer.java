package com.maveric.authenticationauthorizationservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "feignUser", url = "http://localhost:3005/api/v1")
public interface FeignConsumer {
}
