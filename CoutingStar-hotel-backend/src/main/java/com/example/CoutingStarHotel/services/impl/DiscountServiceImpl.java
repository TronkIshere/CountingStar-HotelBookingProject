package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.Discount;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiscountServiceImpl {
    void addDiscount(Discount discountRequest);

    void deleteDiscount(Long discount);

    Discount updateDiscount(Long discountId, int percentDiscount, String discountDescription, LocalDate expirationDate);

    List<Discount> getDiscountNotExpired();
}
