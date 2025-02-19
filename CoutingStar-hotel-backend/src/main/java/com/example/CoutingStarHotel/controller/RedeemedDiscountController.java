package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.response.redeemedDiscount.RedeemedDiscountResponse;
import com.example.CoutingStarHotel.DTO.response.common.ResponseData;
import com.example.CoutingStarHotel.services.RedeemedDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/redeemed-discounts")
public class RedeemedDiscountController {
    private final RedeemedDiscountService redeemedDiscountService;

    @PostMapping("/user/{userId}/discount/{discountId}")
    public ResponseEntity<String> addRedeemedDiscount(@PathVariable Long userId,
                                                      @PathVariable Long discountId) {
        redeemedDiscountService.addRedeemedDiscountByUserId(discountId, userId);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/user/{userId}")
    public ResponseData<List<RedeemedDiscountResponse>> getAllRedeemedDiscounts(@PathVariable Long userId) {
        var result = redeemedDiscountService.getAllRedeemedDiscountByUserId(userId);
        return ResponseData.<List<RedeemedDiscountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/{redeemedDiscountId}/soft-delete")
    public ResponseData<String> softDeleteRedeemedDiscount(@PathVariable Long redeemedDiscountId) {
        var result = redeemedDiscountService.softDelete(redeemedDiscountId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
