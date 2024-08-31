package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.RedeemedDiscountDTO;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.services.impl.RedeemedDiscountServiceImpl;
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
    private final RedeemedDiscountServiceImpl redeemedDiscountServiceImpl;
    @PostMapping("/add/{discountId}/{userId}")
    public ResponseEntity<?> addRedeemedDiscountByUserId(@PathVariable Long userId,
                                                         @PathVariable Long discountId){
        try{
            redeemedDiscountServiceImpl.addRedeemedDiscountByUserId(discountId,userId);
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
            List<RedeemedDiscount> redeemedDiscounts = redeemedDiscountServiceImpl.getAllRedeemedDiscountByUserId(userId);
            List<RedeemedDiscountDTO> redeemedDiscountDTOS = new ArrayList<>();
            for(RedeemedDiscount redeemedDiscount : redeemedDiscounts) {
                RedeemedDiscountDTO ratingResponse = getRedeemedDiscountDTO(redeemedDiscount);
                redeemedDiscountDTOS.add(ratingResponse);
            }
            return ResponseEntity.ok(redeemedDiscountDTOS);
        }catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private RedeemedDiscountDTO getRedeemedDiscountDTO(RedeemedDiscount redeemedDiscount){
        return new RedeemedDiscountDTO(
                redeemedDiscount.getId(),
                redeemedDiscount.isUsed(),
                redeemedDiscount.getDiscount().getDiscountName(),
                redeemedDiscount.getDiscount().getPercentDiscount(),
                redeemedDiscount.getDiscount().getDiscountDescription()
        );
    }
}
