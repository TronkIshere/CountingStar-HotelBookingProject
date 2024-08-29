package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.impl.RedeemedDiscountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedeemedDiscountService implements RedeemedDiscountServiceImpl {
    private final RedeemedDiscountRepository redeemedDiscountRepository;
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

    @Override
    public void createRedeemedDiscountByUserId(Long discountId, Long userId) {
        Discount discount = discountRepository.findById(discountId).get();
        User user = userRepository.findById(userId).get();
        RedeemedDiscount redeemedDiscount = new RedeemedDiscount();
        user.addRedeemedDiscount(redeemedDiscount);
        discount.addRedeemedDiscount(redeemedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
    }
}
