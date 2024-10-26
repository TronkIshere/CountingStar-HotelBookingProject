package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Discount;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiscountService {
    Discount addDiscount(String discountName, Integer percentDiscount, String discountDescription, LocalDate expirationDate);

    void deleteDiscount(Long discount);

    Discount updateDiscount(Long discountId, String discountName, int percentDiscount, String discountDescription, LocalDate expirationDate);

    Page<Discount> getDiscountNotExpired(Integer pageNo, Integer pageSize);

    Optional<Discount> getDiscountById(Long discountId);

    Page<Discount> getAllDiscount(Integer pageNo, Integer pageSize);

    Page<Discount> getDiscountByKeyword(Integer pageNo, Integer pageSize, String keyword);
}