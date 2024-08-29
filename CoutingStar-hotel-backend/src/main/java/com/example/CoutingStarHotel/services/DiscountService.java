package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.services.impl.DiscountServiceImpl;
import com.example.CoutingStarHotel.services.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
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
        discountRequest.setCreateDate(LocalDate.now());
        discountRepository.save(discountRequest);
    }

    @Override
    public Discount updateDiscount(Long discountId, int percentDiscount, String discountDescription, LocalDate expirationDate){
        Discount discount = discountRepository.findById(discountId).get();
        discount.setPercentDiscount(percentDiscount);
        if (discountDescription != null) discount.setDiscountDescription(discountDescription);
        if (expirationDate != null) discount.setExpirationDate(expirationDate);
        return discountRepository.save(discount);
    }

    @Override
    public List<Discount> getDiscountNotExpired() {
        return discountRepository.getDiscountNotExpired(LocalDate.now());
    }

    @Override
    public void deleteDiscount(Long discountId){
        discountRepository.deleteById(discountId);
    }
}
