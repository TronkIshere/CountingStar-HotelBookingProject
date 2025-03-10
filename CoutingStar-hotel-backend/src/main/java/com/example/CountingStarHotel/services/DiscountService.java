package com.example.CountingStarHotel.services;

import com.example.CountingStarHotel.DTO.request.discount.AddDiscountRequest;
import com.example.CountingStarHotel.DTO.request.discount.UpdateDiscountRequest;
import com.example.CountingStarHotel.DTO.response.discount.DiscountResponse;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.entities.Discount;
import com.example.CountingStarHotel.entities.RedeemedDiscount;

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

    String softDelete(Long discountId);
}