package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.impl.RedeemedDiscountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedeemedDiscountService implements RedeemedDiscountServiceImpl {
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
    public List<RedeemedDiscount> getAllRedeemedDiscountByUserId(Long userId) {

        return discountRepository.getAllRedeemedDiscountNotExpiredByUserId(userId, LocalDate.now());
    }

    @Override
    public RedeemedDiscount findRedeemedDiscountById(Long redeemedDiscountId) {
        return redeemedDiscountRepository.findById(redeemedDiscountId).orElseThrow((() -> new ResourceNotFoundException("discount not found")));
    }
}
