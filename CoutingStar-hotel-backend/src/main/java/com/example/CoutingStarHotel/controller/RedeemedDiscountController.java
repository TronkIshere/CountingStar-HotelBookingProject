package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.response.RedeemedDiscountResponse;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.services.RedeemedDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/redeemedDiscount")
public class RedeemedDiscountController {
    private final RedeemedDiscountService redeemedDiscountService;
    @PostMapping("/add/{discountId}/{userId}")
    public ResponseEntity<?> addRedeemedDiscountByUserId(@PathVariable Long userId,
                                                         @PathVariable Long discountId){
        try{
            redeemedDiscountService.addRedeemedDiscountByUserId(discountId,userId);
            return ResponseEntity.ok("Đã nhận được mã giảm giá");

        }catch (InvalidBookingRequestException e){
            System.out.println(ResponseEntity.badRequest().body(e.getMessage()));
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getAllRedeemedDiscountByUserId(@PathVariable Long userId){
        try{
            System.out.println(userId);
            List<RedeemedDiscount> redeemedDiscounts = redeemedDiscountService.getAllRedeemedDiscountByUserId(userId);
            List<RedeemedDiscountResponse> redeemedDiscountDTOS = new ArrayList<>();
            for(RedeemedDiscount redeemedDiscount : redeemedDiscounts) {
                RedeemedDiscountResponse ratingResponse = getRedeemedDiscountDTO(redeemedDiscount);
                redeemedDiscountDTOS.add(ratingResponse);
            }
            return ResponseEntity.ok(redeemedDiscountDTOS);
        }catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private RedeemedDiscountResponse getRedeemedDiscountDTO(RedeemedDiscount redeemedDiscount){
        return new RedeemedDiscountResponse(
                redeemedDiscount.getId(),
                redeemedDiscount.isUsed(),
                redeemedDiscount.getDiscount().getDiscountName(),
                redeemedDiscount.getDiscount().getPercentDiscount(),
                redeemedDiscount.getDiscount().getDiscountDescription()
        );
    }
}
