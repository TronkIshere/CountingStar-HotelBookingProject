package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.InvalidDiscountRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.DTO.DiscountDTO;
import com.example.CoutingStarHotel.services.DiscountService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<DiscountDTO> addDiscount( @RequestParam("discountName") String discountName,
                                          @RequestParam("percentDiscount") Integer percentDiscount,
                                          @RequestParam("discountDescription") String discountDescription,
                                          @RequestParam("expirationDate") LocalDate expirationDate) throws IOException {
            Discount discount = discountService.addDiscount(discountName, percentDiscount, discountDescription, expirationDate);
            return ResponseEntity.ok(getDiscountResponse(discount));
    }

    @GetMapping("/getAllDiscount")
    public ResponseEntity<Page<DiscountDTO>> getAllDiscount(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(defaultValue = "8") Integer pageSize){
        Page<Discount> discounts = discountService.getAllDiscount(pageNo, pageSize);
        Page<DiscountDTO> discountDTOS = discounts.map(this::getDiscountResponse);
        return ResponseEntity.ok(discountDTOS);
    }

    @GetMapping("/getDiscountNotExpired")
    public ResponseEntity<Page<DiscountDTO>> getDiscountNotExpired(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(defaultValue = "15") Integer pageSize){
        Page<Discount> discounts = discountService.getDiscountNotExpired(pageNo, pageSize);
        Page<DiscountDTO> discountDTOS = discounts.map(this::getDiscountResponse);
        return ResponseEntity.ok(discountDTOS);
    }

    @GetMapping("/getDiscountByKeyword/{keyword}")
    public ResponseEntity<Page<DiscountDTO>> getDiscountByKeyword(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(defaultValue = "8") Integer pageSize,
                                                                   @PathVariable String keyword){
        Page<Discount> discounts = discountService.getDiscountByKeyword(pageNo, pageSize, keyword);
        Page<DiscountDTO> discountDTOS = discounts.map(this::getDiscountResponse);
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
