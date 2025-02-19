package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.discount.AddDiscountRequest;
import com.example.CoutingStarHotel.DTO.request.discount.UpdateDiscountRequest;
import com.example.CoutingStarHotel.DTO.response.discount.DiscountResponse;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.DTO.response.common.ResponseData;
import com.example.CoutingStarHotel.services.DiscountService;
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

    @PostMapping("/addDiscount")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<DiscountResponse> addDiscount(@RequestBody AddDiscountRequest request) throws IOException {
        var result = discountService.addDiscount(request);
        return ResponseData.<DiscountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/getAllDiscount")
    public ResponseData<PageResponse<DiscountResponse>> getAllDiscount(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                         @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = discountService.getAllDiscount(pageNo, pageSize);
        return ResponseData.<PageResponse<DiscountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/getDiscountNotExpired")
    public ResponseData<PageResponse<DiscountResponse>> getDiscountNotExpired(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                        @RequestParam(defaultValue = "15") Integer pageSize) {
        var result = discountService.getDiscountNotExpired(pageNo, pageSize);
        return ResponseData.<PageResponse<DiscountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/getDiscountByKeyword/{keyword}")
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

    @GetMapping("/getDiscountById/{discountId}")
    public ResponseData<DiscountResponse> getDiscountById(@PathVariable Long discountId) {
        var result = discountService.getDiscountResponseById(discountId);
        return ResponseData.<DiscountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/update/{discountId}")
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

    @DeleteMapping("/delete/{discountId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/softDelete/{discountId}")
    public ResponseData<String> softDeleteDiscount(@PathVariable Long discountId){
        var result = discountService.softDelete(discountId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
