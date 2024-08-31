package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;

import java.util.List;

public interface RedeemedDiscountServiceImpl {
    void addRedeemedDiscountByUserId(Long discountId, Long userId);

    List<RedeemedDiscount> getAllRedeemedDiscountByUserId(Long userId);

    RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId);
}
