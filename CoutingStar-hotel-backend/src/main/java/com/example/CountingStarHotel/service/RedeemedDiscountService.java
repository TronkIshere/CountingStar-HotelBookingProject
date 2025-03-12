package com.example.CountingStarHotel.service;

import com.example.CountingStarHotel.DTO.response.redeemedDiscount.RedeemedDiscountResponse;
import com.example.CountingStarHotel.entity.RedeemedDiscount;

import java.util.List;

public interface RedeemedDiscountService {
    void addRedeemedDiscountByUserId(Long discountId, Long userId);

    List<RedeemedDiscountResponse> getAllRedeemedDiscountByUserId(Long userId);

    RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId);

    String softDelete(Long redeemedDiscount);
}
