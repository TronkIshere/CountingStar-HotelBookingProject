package com.example.CountingStarHotel.DTO.request.discount;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class AddDiscountRequest implements Serializable {
    @NotBlank
    private String discountName;

    @NotBlank
    private int percentDiscount;

    @NotBlank
    private String discountDescription;

    @NotBlank
    private LocalDate expirationDate;
}
