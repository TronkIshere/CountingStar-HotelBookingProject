package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.services.impl.RedeemedDiscountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/redeemedDiscount")
public class RedeemedDiscountController {
    private final RedeemedDiscountServiceImpl redeemedDiscountServiceImpl;
    @GetMapping("/get/{discountId}/{userId}")
    private ResponseEntity<?> createRedeemedDiscountByUserId(@PathVariable Long userId,
                                                             @PathVariable Long discountId){
        try{
            redeemedDiscountServiceImpl.createRedeemedDiscountByUserId(discountId,userId);
            return ResponseEntity.ok("Đã nhận được mã giảm giá");

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
