package com.example.CoutingStarHotel.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Getter
public class UpdateBookedRoom {
    @NotBlank
    private LocalDate checkInDate;

    @NotBlank
    private LocalDate checkOutDate;

    @NotBlank
    private String guestEmail;

    @NotBlank
    private String guestPhoneNumber;

    @NotBlank
    private String guestFullName;

    @NotBlank
    private int totalNumOfGuest;
}
