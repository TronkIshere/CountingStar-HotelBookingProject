package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.AddDiscountRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateDiscountRequest;
import com.example.CoutingStarHotel.DTO.response.DiscountResponse;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.entities.Discount;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiscountService {
    DiscountResponse addDiscount(AddDiscountRequest request);

    void deleteDiscount(Long discount);

    DiscountResponse updateDiscount(Long discountId, UpdateDiscountRequest request);

    PageResponse<DiscountResponse> getDiscountNotExpired(Integer pageNo, Integer pageSize);

    DiscountResponse getDiscountById(Long discountId);

    PageResponse<DiscountResponse> getAllDiscount(Integer pageNo, Integer pageSize);

    PageResponse<DiscountResponse> getDiscountByKeyword(Integer pageNo, Integer pageSize, String keyword);
}