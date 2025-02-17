package com.example.CoutingStarHotel.DTO.response;

import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.User;
import lombok.*;

@Getter
@Setter
@Builder
public class RedeemedDiscountResponse {
    private Long id;
    private boolean isUsed;
    private Discount discount;
    private User user;
    private BookedRoom bookedRoom;
    private String discountName;
    private int percentDiscount;
    private String discountDescription;
}
