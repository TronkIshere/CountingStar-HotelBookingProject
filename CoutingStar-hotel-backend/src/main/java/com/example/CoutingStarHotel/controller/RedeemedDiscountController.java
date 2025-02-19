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
@RequestMapping("/redeemedDiscount")
public class RedeemedDiscountController {
    private final RedeemedDiscountService redeemedDiscountService;

    @PostMapping("/add/{discountId}/{userId}")
    public ResponseEntity<?> addRedeemedDiscountByUserId(@PathVariable Long userId,
                                                         @PathVariable Long discountId) {
        redeemedDiscountService.addRedeemedDiscountByUserId(discountId, userId);
        return ResponseEntity.ok("success");

    }

    @GetMapping("/list/{userId}")
    public ResponseData<List<RedeemedDiscountResponse>> getAllRedeemedDiscountByUserId(@PathVariable Long userId) {
        var result = redeemedDiscountService.getAllRedeemedDiscountByUserId(userId);
        return ResponseData.<List<RedeemedDiscountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
