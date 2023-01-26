package com.maveric.authenticationauthorizationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {

    @Email
    @NotEmpty
    private String email;

    @Min(6)
    @NotEmpty
    private String password;
}
