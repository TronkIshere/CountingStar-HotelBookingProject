package com.example.CountingStarHotel.mapper;

import com.example.CountingStarHotel.DTO.response.discount.DiscountResponse;
import com.example.CountingStarHotel.entity.Discount;

import java.util.List;

public class DiscountMapper {
    private DiscountMapper() {}

    public static DiscountResponse toDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .discountName(discount.getDiscountName())
                .percentDiscount(discount.getPercentDiscount())
                .discountDescription(discount.getDiscountDescription())
                .createDate(discount.getCreateDate())
                .expirationDate(discount.getExpirationDate())
                .build();
    }

    public static List<DiscountResponse> discountResponses(List<Discount> discounts) {
        return discounts.stream()
                .map(DiscountMapper::toDiscountResponse)
                .toList();
    }
}
