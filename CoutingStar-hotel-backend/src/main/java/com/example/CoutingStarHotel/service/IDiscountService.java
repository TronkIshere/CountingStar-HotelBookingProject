package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.Discount;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface IDiscountService {
    void addDiscount(Long id, Discount discountRequest);

    void deleteDiscount(Long discount);

    Optional<Discount> getDiscountByRoomId(Long roomId);

    Discount updateDiscount(Long discountId, int percentDiscount, String discountDescription);
}
