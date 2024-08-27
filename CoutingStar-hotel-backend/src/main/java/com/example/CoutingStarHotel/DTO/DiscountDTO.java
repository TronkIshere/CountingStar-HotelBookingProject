package com.example.CoutingStarHotel.DTO;

import com.example.CoutingStarHotel.entities.Room;

public class DiscountDTO {
    private Long id;
    private int percentDiscount;
    private String discountDescription;
    private Room room;

    public DiscountDTO(Long id, int percentDiscount, String discountDescription) {
        this.id = id;
        this.percentDiscount = percentDiscount;
        this.discountDescription = discountDescription;
    }
}
