package com.maveric.authenticationauthorizationservice.dto;

import com.maveric.authenticationauthorizationservice.constant.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;
}
