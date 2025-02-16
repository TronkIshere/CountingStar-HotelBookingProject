package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.response.RedeemedDiscountResponse;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;

import java.util.List;

public interface RedeemedDiscountService {
    void addRedeemedDiscountByUserId(Long discountId, Long userId);

    List<RedeemedDiscountResponse> getAllRedeemedDiscountByUserId(Long userId);

    RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId);
}
