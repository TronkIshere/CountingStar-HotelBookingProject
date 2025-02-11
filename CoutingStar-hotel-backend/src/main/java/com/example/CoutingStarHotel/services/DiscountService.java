package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.AddDiscountRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateDiscountRequest;
import com.example.CoutingStarHotel.DTO.response.DiscountResponse;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;

import java.time.LocalDate;
import java.util.List;

public interface DiscountService {
    DiscountResponse addDiscount(AddDiscountRequest request);

    void deleteDiscount(Long discount);

    DiscountResponse updateDiscount(Long discountId, UpdateDiscountRequest request);

    PageResponse<DiscountResponse> getDiscountNotExpired(Integer pageNo, Integer pageSize);

    Discount getDiscountById(Long discountId);

    PageResponse<DiscountResponse> getAllDiscount(Integer pageNo, Integer pageSize);

    PageResponse<DiscountResponse> getDiscountByKeyword(Integer pageNo, Integer pageSize, String keyword);

    DiscountResponse getDiscountResponseById(Long discountId);

    List<RedeemedDiscount> getAllRedeemedDiscountNotExpiredByUserId(Long userId, LocalDate now);
}