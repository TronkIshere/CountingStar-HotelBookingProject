package com.example.CoutingStarHotel.DTO.request.user;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UploadUserRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String phoneNumber;
}
