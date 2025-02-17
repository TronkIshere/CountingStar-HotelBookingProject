package com.example.CoutingStarHotel.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class UpdateDiscountRequest implements Serializable {
    @NotBlank
    private String discountName;

    @NotBlank
    private int percentDiscount;

    @NotBlank
    private String discountDescription;

    @NotBlank
    private LocalDate expirationDate;
}
