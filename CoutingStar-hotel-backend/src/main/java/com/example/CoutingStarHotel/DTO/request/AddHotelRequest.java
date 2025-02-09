package com.example.CoutingStarHotel.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
public class AddHotelRequest implements Serializable {
    @NotBlank
    private String hotelName;

    @NotBlank
    private String city;

    @NotBlank
    private String hotelLocation;

    @NotBlank
    private String hotelDescription;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private MultipartFile photo;
}
