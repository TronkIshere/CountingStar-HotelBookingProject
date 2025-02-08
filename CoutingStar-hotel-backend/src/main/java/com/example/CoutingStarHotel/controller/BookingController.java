package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.SaveBookingRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateBookedRoom;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.DTO.response.ResponseData;
import com.example.CoutingStarHotel.DTO.response.BookingResponse;
import com.example.CoutingStarHotel.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/all-bookings")
    public ResponseData<PageResponse<BookingResponse>> getAllBookings(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                      @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = bookingService.getAllBookings(pageNo, pageSize);
        return ResponseData.<PageResponse<BookingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/getAllBookingByKeywordAndHotelId/{hotelId}/{keyword}")
    public ResponseData<PageResponse<BookingResponse>> getAllBookingByKeywordAndHotelId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                        @RequestParam(defaultValue = "8") Integer pageSize,
                                                                                        @PathVariable Long hotelId,
                                                                                        @PathVariable String keyword) {
        var result = bookingService.getAllBookingByKeywordAndHotelId(pageNo, pageSize, hotelId, keyword);
        return ResponseData.<PageResponse<BookingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseData<BookingResponse> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        var result = bookingService.findByBookingConfirmationCode(confirmationCode);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseData<BookingResponse> getBookingByBookingId(@PathVariable Long bookingId) {
        var result = bookingService.findByBookingId(bookingId);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotel/{hotelId}/booking")
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

    @PostMapping("/room/{roomId}/booking")
    public ResponseData<BookingResponse> saveBooking(@PathVariable Long roomId,
                                                       @RequestParam SaveBookingRequest request,
                                                       @RequestParam(required = false) Long userId,
                                                       @RequestParam(required = false) Long redeemedDiscountId) {
        var result = bookingService.saveBooking(roomId, request, userId, redeemedDiscountId);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/booking/{bookingId}/update")
    public ResponseData<BookingResponse> updateBookedRoom(@PathVariable Long bookingId,
                                                            @RequestParam UpdateBookedRoom request) {
        var result = bookingService.updateBooked(bookingId, request);
        return ResponseData.<BookingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/user/{userId}/bookings")
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

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

}
