package com.example.CountingStarHotel.DTO.request.rating;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateRatingRequest implements Serializable {
    @NotBlank
    private int star;

    @NotBlank
    private String comment;
}
