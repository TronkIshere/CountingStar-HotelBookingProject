package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.services.DiscountService;
import com.example.CoutingStarHotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final RoomService roomService;
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
    public Page<Discount> getDiscountNotExpired(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return discountRepository.getDiscountNotExpired(pageable, LocalDate.now());
    }

    @Override
    public Optional<Discount> getDiscountById(Long discountId) {
        return Optional.ofNullable(discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("discount not found")));
    }

    @Override
    public Page<Discount> getAllDiscount(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return discountRepository.findAll(pageable);
    }

    @Override
    public Page<Discount> getDiscountByKeyword(Integer pageNo, Integer pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return discountRepository.getDiscountByKeyword(pageable, keyword);
    }

    @Override
    public void deleteDiscount(Long discountId){
        discountRepository.deleteById(discountId);
    }
}
