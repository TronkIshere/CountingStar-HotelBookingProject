package com.example.CountingStarHotel.controller;

import com.example.CountingStarHotel.DTO.request.bookedroom.SaveBookingRequest;
import com.example.CountingStarHotel.DTO.request.bookedroom.UpdateBookedRoom;
import com.example.CountingStarHotel.DTO.response.bookedroom.BookingResponse;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.DTO.response.common.ResponseData;
import com.example.CountingStarHotel.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public ResponseData<PageResponse<BookingResponse>> getAllBookings(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                      @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = bookingService.getAllBookings(pageNo, pageSize);
        return ResponseData.<PageResponse<BookingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/search")
    public ResponseData<PageResponse<BookingResponse>> searchBookings(@RequestParam Long hotelId,
                                                                      @RequestParam String keyword,
                                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                                      @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = bookingService.getAllBookingByKeywordAndHotelId(pageNo, pageSize, hotelId, keyword);
        return ResponseData.<PageResponse<BookingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/{confirmationCode}/confirmation")
    public ResponseData<BookingResponse> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        var result = bookingService.findByBookingConfirmationCode(confirmationCode);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/{bookingId}")
    public ResponseData<BookingResponse> getBookingByBookingId(@PathVariable Long bookingId) {
        var result = bookingService.findBookingResponseById(bookingId);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseData<PageResponse<BookingResponse>> getBookingByHotelId(@PathVariable Long hotelId,
                                                                           @RequestParam(defaultValue = "0") Integer pageNo,
                                                                           @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = bookingService.getAllBookingsByHotelId(pageNo, pageSize, hotelId);
        return ResponseData.<PageResponse<BookingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PostMapping("/rooms/{roomId}")
    public ResponseData<BookingResponse> saveBooking(@PathVariable Long roomId,
                                                     @RequestBody SaveBookingRequest request,
                                                     @RequestParam(required = false) Long userId,
                                                     @RequestParam(required = false) Long redeemedDiscountId) {
        var result = bookingService.saveBooking(roomId, request, userId, redeemedDiscountId);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/{bookingId}")
    public ResponseData<BookingResponse> updateBookedRoom(@PathVariable Long bookingId,
                                                          @RequestBody UpdateBookedRoom request) {
        var result = bookingService.updateBooked(bookingId, request);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/users/{userId}")
    public ResponseData<PageResponse<BookingResponse>> getBookingsByUserId(@PathVariable Long userId,
                                                                           @RequestParam(defaultValue = "0") Integer pageNo,
                                                                           @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = bookingService.getBookingsByUserId(pageNo, pageSize, userId);
        return ResponseData.<PageResponse<BookingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    @PutMapping("/{bookingId}/soft-delete")
    public ResponseData<String> softDeleteBooking(@PathVariable Long bookingId) {
        var result = bookingService.softDelete(bookingId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}