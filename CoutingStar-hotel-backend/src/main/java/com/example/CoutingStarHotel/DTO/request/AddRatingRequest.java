package com.example.CoutingStarHotel.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class AddRatingRequest implements Serializable {
    @NotBlank
    private int star;

    @NotBlank
    private String comment;

    @NotBlank
    private LocalDate rateDay;
}
