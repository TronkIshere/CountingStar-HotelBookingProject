package com.example.CoutingStarHotel.DTO.response.discount;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DiscountResponse implements Serializable {
    private Long id;
    private String discountName;
    private int percentDiscount;
    private String discountDescription;
    private LocalDate createDate;
    private LocalDate expirationDate;
}
