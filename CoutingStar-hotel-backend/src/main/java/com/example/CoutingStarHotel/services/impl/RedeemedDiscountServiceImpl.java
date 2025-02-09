package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.response.RedeemedDiscountResponse;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.mapper.RedeemedDiscountMapper;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.RedeemedDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedeemedDiscountServiceImpl implements RedeemedDiscountService {
    private final RedeemedDiscountRepository redeemedDiscountRepository;
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

    @Override
    public void addRedeemedDiscountByUserId(Long discountId, Long userId) {
        Discount discount = discountRepository.findById(discountId).get();
        User user = userRepository.findById(userId).get();

        if (redeemedDiscountRepository.existsByUserIdAndDiscountId(userId, discountId)) {
            throw new InvalidBookingRequestException("Người dùng đã nhận mã giảm giá này rồi, hãy kiếm mã giảm giá khác");
        }

        RedeemedDiscount redeemedDiscount = new RedeemedDiscount();
        user.addRedeemedDiscount(redeemedDiscount);
        discount.addRedeemedDiscount(redeemedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
    }

    @Override
    public List<RedeemedDiscountResponse> getAllRedeemedDiscountByUserId(Long userId) {
        List<RedeemedDiscount> redeemedDiscountList = discountRepository.getAllRedeemedDiscountNotExpiredByUserId(userId, LocalDate.now());
        return RedeemedDiscountMapper.redeemedDiscountResponses(redeemedDiscountList);
    }

    @Override
    public RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId) {
        return redeemedDiscountRepository.findById(redeemedDiscountId).orElseThrow((() -> new ResourceNotFoundException("discount not found")));
    }
}
