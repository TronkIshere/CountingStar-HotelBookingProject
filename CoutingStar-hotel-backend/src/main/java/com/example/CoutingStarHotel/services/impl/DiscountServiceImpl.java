package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.request.AddDiscountRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateDiscountRequest;
import com.example.CoutingStarHotel.DTO.response.DiscountResponse;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.mapper.DiscountMapper;
import com.example.CoutingStarHotel.repositories.DiscountRepository;
import com.example.CoutingStarHotel.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    @Override
    public DiscountResponse addDiscount(AddDiscountRequest request){
        Discount discount = new Discount();
        discount.setDiscountName(request.getDiscountName());
        discount.setPercentDiscount(request.getPercentDiscount());
        discount.setDiscountDescription(request.getDiscountDescription());
        discount.setExpirationDate(request.getExpirationDate());
        discountRepository.save(discount);
        return DiscountMapper.toDiscountResponse(discount);
    }

    @Override
    public DiscountResponse updateDiscount(Long discountId, UpdateDiscountRequest request){
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new NoSuchElementException("Discount not found with ID: " + discountId));
        discount.setPercentDiscount(request.getPercentDiscount());
        discount.setDiscountName(request.getDiscountName());
        discount.setDiscountDescription(request.getDiscountDescription());
        discount.setExpirationDate(request.getExpirationDate());
        discountRepository.save(discount);
        return DiscountMapper.toDiscountResponse(discount);
    }

    @Override
    public PageResponse<DiscountResponse> getDiscountNotExpired(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Discount> discountPage = discountRepository.getDiscountNotExpired(pageable, LocalDate.now());

        List<Discount> discountList = discountPage.getContent();

        return PageResponse.<DiscountResponse>builder()
                .currentPage(pageNo)
                .pageSize((pageable.getPageSize()))
                .totalPages(discountPage.getTotalPages())
                .totalElements(discountPage.getTotalElements())
                .data(DiscountMapper.discountResponses(discountList))
                .build();
    }

    public Discount getDiscountById(Long discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
    }

    @Override
    public PageResponse<DiscountResponse> getAllDiscount(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Discount> discountPage = discountRepository.findAll(pageable);

        List<Discount> discountList = discountPage.getContent();

        return PageResponse.<DiscountResponse>builder()
                .currentPage(pageNo)
                .pageSize((pageable.getPageSize()))
                .totalPages(discountPage.getTotalPages())
                .totalElements(discountPage.getTotalElements())
                .data(DiscountMapper.discountResponses(discountList))
                .build();
    }

    @Override
    public PageResponse<DiscountResponse> getDiscountByKeyword(Integer pageNo, Integer pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Discount> discountPage = discountRepository.getDiscountByKeyword(pageable, keyword);

        List<Discount> discountList = discountPage.getContent();

        return PageResponse.<DiscountResponse>builder()
                .currentPage(pageNo)
                .pageSize((pageable.getPageSize()))
                .totalPages(discountPage.getTotalPages())
                .totalElements(discountPage.getTotalElements())
                .data(DiscountMapper.discountResponses(discountList))
                .build();
    }

    @Override
    public DiscountResponse getDiscountResponseById(Long discountId) {
        return DiscountMapper.toDiscountResponse(getDiscountById(discountId));
    }

    @Override
    public List<RedeemedDiscount> getAllRedeemedDiscountNotExpiredByUserId(Long userId, LocalDate now) {
        return discountRepository.getAllRedeemedDiscountNotExpiredByUserId(userId, now);
    }

    @Override
    public void deleteDiscount(Long discountId){
        discountRepository.deleteById(discountId);
    }
}
