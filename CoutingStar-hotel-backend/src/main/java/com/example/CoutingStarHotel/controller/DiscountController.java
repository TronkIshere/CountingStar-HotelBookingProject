package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.InvalidDiscountRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.response.DiscountResponse;
import com.example.CoutingStarHotel.services.impl.DiscountServiceImpl;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountServiceImpl discountServiceImpl;
    @PostMapping("/discount/{roomId}/addDiscount")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    private ResponseEntity<?> addDiscount(@PathVariable Long roomId,
                                          @RequestBody Discount discountRequest) throws SQLException, IOException {
        try{
            discountServiceImpl.addDiscount(roomId, discountRequest);
            return ResponseEntity.ok("Your discount have been set successfully");
        } catch (InvalidDiscountRequestException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/discount/{roomId}")
    private ResponseEntity<Optional<DiscountResponse>> getDiscountByRoomId(@PathVariable Long roomId){
        Optional<Discount> theDiscount = discountServiceImpl.getDiscountByRoomId(roomId);
        return theDiscount.map(discount -> {
            DiscountResponse discountResponse = getDiscountResponse(discount);
            return  ResponseEntity.ok(Optional.of(discountResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Discount not exist"));
    }

    @PutMapping("/update/{discountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<DiscountResponse> updateDiscount(@PathVariable Long discountId,
                                                           @RequestParam(required = false) int percentDiscount,
                                                           @RequestParam(required = false) String discountDescription) throws SQLException, java.io.IOException {
        Discount discount = discountServiceImpl.updateDiscount(discountId, percentDiscount, discountDescription);
        DiscountResponse discountResponse = getDiscountResponse(discount);
        return ResponseEntity.ok(discountResponse);
    }

    @DeleteMapping("/discount/{discount}/deleteDiscount")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    private ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId){
        discountServiceImpl.deleteDiscount(discountId);
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
