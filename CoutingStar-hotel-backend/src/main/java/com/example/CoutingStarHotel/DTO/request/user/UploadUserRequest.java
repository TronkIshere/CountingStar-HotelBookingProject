package com.example.CoutingStarHotel.DTO.request.user;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UploadUserRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}
