package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.services.impl.DiscountServiceImpl;
import com.example.CoutingStarHotel.services.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService implements DiscountServiceImpl {
    private final RoomServiceImpl roomService;
    private final RoomRepository roomRepository;
    private final DiscountRepository discountRepository;

    @Override
    public void addDiscount(Discount discountRequest){
        discountRepository.save(discountRequest);
    }

    @Override
    public Discount updateDiscount(Long discountId, String discountName, int percentDiscount, String discountDescription, LocalDate expirationDate){
        Discount discount = discountRepository.findById(discountId).get();
        discount.setPercentDiscount(percentDiscount);
        if(discountName != null) discount.setDiscountName(discountName);
        if (discountDescription != null) discount.setDiscountDescription(discountDescription);
        if (expirationDate != null) discount.setExpirationDate(expirationDate);
        return discountRepository.save(discount);
    }

    @Override
    public List<Discount> getDiscountNotExpired() {
        return discountRepository.getDiscountNotExpired(LocalDate.now());
    }

    @Override
    public Optional<Discount> getDiscountById(Long discountId) {
        return Optional.ofNullable(discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("discount not found")));
    }

    @Override
    public void deleteDiscount(Long discountId){
        discountRepository.deleteById(discountId);
    }
}
