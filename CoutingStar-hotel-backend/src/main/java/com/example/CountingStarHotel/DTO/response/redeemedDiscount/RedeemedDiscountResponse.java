package com.example.CountingStarHotel.DTO.response.redeemedDiscount;

import com.example.CountingStarHotel.entity.BookedRoom;
import com.example.CountingStarHotel.entity.Discount;
import com.example.CountingStarHotel.entity.User;
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
