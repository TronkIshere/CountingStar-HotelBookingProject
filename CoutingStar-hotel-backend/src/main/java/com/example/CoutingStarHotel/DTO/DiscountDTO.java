package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DiscountDTO {
    private Long id;
    private String discountName;
    private int percentDiscount;
    private String discountDescription;
    private LocalDate createDate;
    private LocalDate expirationDate;

    public DiscountDTO(Long id, String discountName, int percentDiscount, String discountDescription, LocalDate createDate, LocalDate expirationDate) {
        this.id = id;
        this.discountName = discountName;
        this.percentDiscount = percentDiscount;
        this.discountDescription = discountDescription;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
    }
}
