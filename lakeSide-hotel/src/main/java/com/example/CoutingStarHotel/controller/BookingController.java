package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exeption.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exeption.ResourceNotFoundException;
import com.example.CoutingStarHotel.model.BookedRoom;
import com.example.CoutingStarHotel.model.Room;
import com.example.CoutingStarHotel.response.BookingResponse;
import com.example.CoutingStarHotel.response.RoomResponse;
import com.example.CoutingStarHotel.security.user.HotelUserDetails;
import com.example.CoutingStarHotel.service.IBookingService;
import com.example.CoutingStarHotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final IBookingService bookingService;
    private final IRoomService roomService;
    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest,
                                         @RequestParam(required = false) Long userId){
        try{
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest, userId);
            return ResponseEntity.ok(
                    "Room booked successfully, Your booking confirmation code is :" + confirmationCode);

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookedRoom> bookings = bookingService.getBookingsByUserId(userId);
        List<BookingResponse> bookingResponses = bookings.stream()
                .map(this::getBookingResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingResponses);
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice(),
                theRoom.getRoomDescription());
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getBookingConfirmationCode(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumOfGuest(),
                booking.getGuestFullName(),
                room);
    }
}
