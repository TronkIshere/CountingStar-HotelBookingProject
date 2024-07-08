package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.InvalidDiscountRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.model.Discount;
import com.example.CoutingStarHotel.response.DiscountResponse;
import com.example.CoutingStarHotel.service.IDiscountService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final IDiscountService discountService;
    @PostMapping("/discount/{roomId}/addDiscount")
    private ResponseEntity<?> addDiscount(@PathVariable Long roomId,
                                          @RequestBody Discount discountRequest) throws SQLException, IOException {
        try{
            discountService.addDiscount(roomId, discountRequest);
            return ResponseEntity.ok("Your discount have been set successfully");
        } catch (InvalidDiscountRequestException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/discount/{roomId}")
    private ResponseEntity<Optional<DiscountResponse>> getDiscountByRoomId(@PathVariable Long roomId){
        Optional<Discount> theDiscount = discountService.getDiscountByRoomId(roomId);
        return theDiscount.map(discount -> {
            DiscountResponse discountResponse = getDiscountResponse(discount);
            return  ResponseEntity.ok(Optional.of(discountResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Discount not exist"));
    }

    @PutMapping("/update/{discountId}")
    public ResponseEntity<DiscountResponse> updateDiscount(@PathVariable Long discountId,
                                                           @RequestParam(required = false) int percentDiscount,
                                                           @RequestParam(required = false) String discountDescription) throws SQLException, java.io.IOException {
        Discount discount = discountService.updateDiscount(discountId, percentDiscount, discountDescription);
        DiscountResponse discountResponse = getDiscountResponse(discount);
        return ResponseEntity.ok(discountResponse);
    }

    @DeleteMapping("/discount/{discount}/deleteDiscount")
    private ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId){
        discountService.deleteDiscount(discountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DiscountResponse getDiscountResponse(Discount discount){
        return new DiscountResponse(
                discount.getId(),
                discount.getPercentDiscount(),
                discount.getDiscountDescription()
        );
    }
}
