package com.example.CountingStarHotel.services;

import com.example.CountingStarHotel.DTO.response.redeemedDiscount.RedeemedDiscountResponse;
import com.example.CountingStarHotel.entities.RedeemedDiscount;

import java.util.List;

public interface RedeemedDiscountService {
    void addRedeemedDiscountByUserId(Long discountId, Long userId);

    List<RedeemedDiscountResponse> getAllRedeemedDiscountByUserId(Long userId);

    RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId);

    String softDelete(Long redeemedDiscount);
}
