package com.example.CoutingStarHotel.DTO.request.bookedroom;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class SaveBookingRequest implements Serializable {
    @NotBlank
    private LocalDate checkInDate;
    @NotBlank
    private LocalDate checkOutDate;
    @NotBlank
    private String guestFullName;
    @NotBlank
    private String guestEmail;
    @NotBlank
    private int NumOfAdults;
    @NotBlank
    private int NumOfChildren;
    @NotBlank
    private int totalNumOfGuest;
    @NotBlank
    private String guestPhoneNumber;
}
