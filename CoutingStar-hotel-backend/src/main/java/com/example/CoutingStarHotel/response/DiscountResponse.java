package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.Room;

public class DiscountResponse {
    private Long id;
    private int percentDiscount;
    private String discountDescription;
    private Room room;

    public DiscountResponse(Long id, int percentDiscount, String discountDescription) {
        this.id = id;
        this.percentDiscount = percentDiscount;
        this.discountDescription = discountDescription;
    }
}
