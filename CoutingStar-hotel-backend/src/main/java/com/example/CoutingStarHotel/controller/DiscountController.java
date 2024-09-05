package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.InvalidDiscountRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.DTO.DiscountDTO;
import com.example.CoutingStarHotel.services.DiscountService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;
    @PostMapping("/addDiscount")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<?> addDiscount( @RequestParam("discountName") String discountName,
                                          @RequestParam("percentDiscount") Integer percentDiscount,
                                          @RequestParam("discountDescription") String discountDescription,
                                          @RequestParam("expirationDate") LocalDate expirationDate) throws SQLException, IOException {
        try{
            Discount discountRequest = Discount.builder()
                    .discountName(discountName)
                    .percentDiscount(percentDiscount)
                    .discountDescription(discountDescription)
                    .expirationDate(expirationDate)
                    .createDate(LocalDate.now())
                    .build();
            discountService.addDiscount(discountRequest);
            return ResponseEntity.ok("Your discount have been set successfully");
        } catch (InvalidDiscountRequestException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/getAllDiscount")
    public ResponseEntity<List<DiscountDTO>> getDiscountNotExpired(){
        List<Discount> discounts = discountService.getDiscountNotExpired();
        List<DiscountDTO> discountDTOS = new ArrayList<>();
        for(Discount discount : discounts){
            DiscountDTO discountResponse = getDiscountResponse(discount);
            discountDTOS.add(discountResponse);
        }
        return ResponseEntity.ok(discountDTOS);
    }

    @GetMapping("/getDiscountById/{discountId}")
    public ResponseEntity<Optional<DiscountDTO>> getDiscountById(@PathVariable Long discountId){
        Optional<Discount> discountDTO = discountService.getDiscountById(discountId);
        return discountDTO.map(discount -> {
            DiscountDTO discountResponse = getDiscountResponse(discount);
            return  ResponseEntity.ok(Optional.of(discountResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    @PutMapping("/update/{discountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable Long discountId,
                                                      @RequestParam String discountName,
                                                      @RequestParam int percentDiscount,
                                                      @RequestParam String discountDescription,
                                                      @RequestParam LocalDate expirationDate) throws SQLException, java.io.IOException {
        Discount discount = discountService.updateDiscount(discountId, discountName, percentDiscount, discountDescription, expirationDate);
        DiscountDTO discountResponse = getDiscountResponse(discount);
        return ResponseEntity.ok(discountResponse);
    }

    @DeleteMapping("/delete/{discountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId){
            discountService.deleteDiscount(discountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DiscountDTO getDiscountResponse(Discount discount){
        return new DiscountDTO(
                discount.getId(),
                discount.getDiscountName(),
                discount.getPercentDiscount(),
                discount.getDiscountDescription(),
                discount.getCreateDate(),
                discount.getExpirationDate()
        );
    }
}
