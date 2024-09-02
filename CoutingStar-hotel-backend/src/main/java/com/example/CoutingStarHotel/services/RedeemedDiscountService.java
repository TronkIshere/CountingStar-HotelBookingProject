package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.RedeemedDiscount;

import java.util.List;

public interface RedeemedDiscountService {
    void addRedeemedDiscountByUserId(Long discountId, Long userId);
    List<RedeemedDiscount> getAllRedeemedDiscountByUserId(Long userId);
    RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId);
}
