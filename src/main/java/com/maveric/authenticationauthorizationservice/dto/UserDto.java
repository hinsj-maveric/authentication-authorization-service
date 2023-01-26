package com.maveric.authenticationauthorizationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maveric.authenticationauthorizationservice.constant.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;
}
