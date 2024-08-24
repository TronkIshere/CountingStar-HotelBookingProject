package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.services.impl.DiscountServiceImpl;
import com.example.CoutingStarHotel.services.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService implements DiscountServiceImpl {
    private final RoomServiceImpl roomService;
    private final RoomRepository roomRepository;
    private final DiscountRepository discountRepository;

    @Override
    public void addDiscount(Long roomId, Discount discountRequest){
        Room room = roomService.getRoomById(roomId).get();
        room.addDiscount(discountRequest);
        discountRepository.save(discountRequest);
    }

    @Override
    public Optional<Discount> getDiscountByRoomId(Long roomId){
        Discount theDiscount = roomRepository.findById(roomId).get().getDiscount();
        return Optional.ofNullable(theDiscount);
    }

    @Override
    public Discount updateDiscount(Long discountId, int percentDiscount, String discountDescription){
        Discount discount = discountRepository.findById(discountId).get();
        discount.setPercentDiscount(percentDiscount);
        if (discountDescription != null) discount.setDiscountDescription(discountDescription);
        return discountRepository.save(discount);
    }

    @Override
    public void deleteDiscount(Long discountId){
        discountRepository.deleteById(discountId);
    }
}
