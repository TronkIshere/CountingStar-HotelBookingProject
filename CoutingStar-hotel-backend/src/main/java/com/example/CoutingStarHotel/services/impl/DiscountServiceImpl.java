package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.Discount;

import java.util.Optional;

public interface DiscountServiceImpl {
    void addDiscount(Long id, Discount discountRequest);

    void deleteDiscount(Long discount);

    Optional<Discount> getDiscountByRoomId(Long roomId);

    Discount updateDiscount(Long discountId, int percentDiscount, String discountDescription);
}
