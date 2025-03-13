package com.example.CountingStarHotel.controller;

import com.example.CountingStarHotel.DTO.request.discount.AddDiscountRequest;
import com.example.CountingStarHotel.DTO.request.discount.UpdateDiscountRequest;
import com.example.CountingStarHotel.DTO.response.discount.DiscountResponse;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.DTO.response.common.ResponseData;
import com.example.CountingStarHotel.service.DiscountService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<DiscountResponse> addDiscount(@RequestBody AddDiscountRequest request) throws IOException {
        var result = discountService.addDiscount(request);
        return ResponseData.<DiscountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/")
    public ResponseData<PageResponse<DiscountResponse>> getAllDiscount(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                       @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = discountService.getAllDiscount(pageNo, pageSize);
        return ResponseData.<PageResponse<DiscountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/not-expired")
    public ResponseData<PageResponse<DiscountResponse>> getDiscountNotExpired(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                              @RequestParam(defaultValue = "15") Integer pageSize) {
        var result = discountService.getDiscountNotExpired(pageNo, pageSize);
        return ResponseData.<PageResponse<DiscountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/search/{keyword}")
    public ResponseData<PageResponse<DiscountResponse>> getDiscountByKeyword(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                             @RequestParam(defaultValue = "8") Integer pageSize,
                                                                             @PathVariable String keyword) {
        var result = discountService.getDiscountByKeyword(pageNo, pageSize, keyword);
        return ResponseData.<PageResponse<DiscountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/{discountId}")
    public ResponseData<DiscountResponse> getDiscountById(@PathVariable Long discountId) {
        var result = discountService.getDiscountResponseById(discountId);
        return ResponseData.<DiscountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/{discountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<DiscountResponse> updateDiscount(@PathVariable Long discountId,
                                                         @RequestBody UpdateDiscountRequest request) {
        var result = discountService.updateDiscount(discountId, request);
        return ResponseData.<DiscountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/{discountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{discountId}/soft-delete")
    public ResponseData<String> softDeleteDiscount(@PathVariable Long discountId) {
        var result = discountService.softDelete(discountId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}

