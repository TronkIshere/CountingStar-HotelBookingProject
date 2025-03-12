package com.example.CountingStarHotel.service.impl;

import com.example.CountingStarHotel.DTO.request.discount.AddDiscountRequest;
import com.example.CountingStarHotel.DTO.request.discount.UpdateDiscountRequest;
import com.example.CountingStarHotel.DTO.response.discount.DiscountResponse;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.entity.Discount;
import com.example.CountingStarHotel.entity.RedeemedDiscount;
import com.example.CountingStarHotel.exception.ApplicationException;
import com.example.CountingStarHotel.exception.ErrorCode;
import com.example.CountingStarHotel.mapper.DiscountMapper;
import com.example.CountingStarHotel.repository.DiscountRepository;
import com.example.CountingStarHotel.service.DiscountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountServiceImpl implements DiscountService {
    DiscountRepository discountRepository;

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
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_DISCOUNT_REQUEST));
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
    public String softDelete(Long discountId) {
        LocalDateTime deletedAt = LocalDateTime.now();
        Discount discount = getDiscountById(discountId);
        discount.setDeletedAt(deletedAt);
        discountRepository.save(discount);
        return "Discount with ID " + discountId + " has been soft deleted at " + deletedAt;
    }

    @Override
    public void deleteDiscount(Long discountId){
        discountRepository.deleteById(discountId);
    }
}
