package com.example.CountingStarHotel.services.impl;

import com.example.CountingStarHotel.DTO.response.redeemedDiscount.RedeemedDiscountResponse;
import com.example.CountingStarHotel.entities.Discount;
import com.example.CountingStarHotel.entities.RedeemedDiscount;
import com.example.CountingStarHotel.entities.User;
import com.example.CountingStarHotel.exception.ApplicationException;
import com.example.CountingStarHotel.exception.ErrorCode;
import com.example.CountingStarHotel.mapper.RedeemedDiscountMapper;
import com.example.CountingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CountingStarHotel.services.DiscountService;
import com.example.CountingStarHotel.services.RedeemedDiscountService;
import com.example.CountingStarHotel.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedeemedDiscountServiceImpl implements RedeemedDiscountService {
    RedeemedDiscountRepository redeemedDiscountRepository;
    UserService userService;
    DiscountService discountService;

    @Override
    public void addRedeemedDiscountByUserId(Long discountId, Long userId) {
        Discount discount = discountService.getDiscountById(discountId);
        User user = userService.getUserById(userId);
        if (redeemedDiscountRepository.existsByUserIdAndDiscountId(userId, discountId)) {
            throw new ApplicationException(ErrorCode.INVALID_DISCOUNT_REQUEST);
        }

        RedeemedDiscount redeemedDiscount = new RedeemedDiscount();
        user.addRedeemedDiscount(redeemedDiscount);
        discount.addRedeemedDiscount(redeemedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
    }

    @Override
    public List<RedeemedDiscountResponse> getAllRedeemedDiscountByUserId(Long userId) {
        List<RedeemedDiscount> redeemedDiscountList = discountService.getAllRedeemedDiscountNotExpiredByUserId(userId, LocalDate.now());
        return RedeemedDiscountMapper.redeemedDiscountResponses(redeemedDiscountList);
    }

    @Override
    public RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId) {
        return redeemedDiscountRepository.findById(redeemedDiscountId).orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_DISCOUNT_REQUEST));
    }

    @Override
    public String softDelete(Long redeemedDiscountId) {
        LocalDateTime deleteAt = LocalDateTime.now();
        RedeemedDiscount redeemedDiscount = findById(redeemedDiscountId);
        redeemedDiscount.setDeletedAt(deleteAt);
        return "RedeemedDiscount with id " + redeemedDiscountId + " has been deleted at " + deleteAt;
    }

    public RedeemedDiscount findById(Long id){
        return redeemedDiscountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating with ID " + id + " not found"));
    }
}
