package com.example.CoutingStarHotel.mapper;

import com.example.CoutingStarHotel.DTO.response.redeemedDiscount.RedeemedDiscountResponse;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;

import java.util.List;

public class RedeemedDiscountMapper {

    private RedeemedDiscountMapper() {
    }

    public static RedeemedDiscountResponse toDiscountResponse(RedeemedDiscount redeemedDiscount) {
        return RedeemedDiscountResponse.builder()
                .id(redeemedDiscount.getId())
                .isUsed(redeemedDiscount.isUsed())
                .discount(redeemedDiscount.getDiscount())
                .user(redeemedDiscount.getUser())
                .bookedRoom(redeemedDiscount.getBookedRoom())
                .percentDiscount(redeemedDiscount.getDiscount().getPercentDiscount())
                .discountDescription(redeemedDiscount.getDiscount().getDiscountDescription())
                .build();
    }

    public static List<RedeemedDiscountResponse> redeemedDiscountResponses(List<RedeemedDiscount> redeemedDiscounts) {
        return redeemedDiscounts.stream()
                .map(RedeemedDiscountMapper::toDiscountResponse)
                .toList();
    }
}
