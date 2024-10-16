package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.DTO.BookingDTO;
import com.example.CoutingStarHotel.DTO.RoomDTO;
import com.example.CoutingStarHotel.services.BookingService;
import com.example.CoutingStarHotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;
    @GetMapping("/all-bookings")
    public ResponseEntity<Page<BookingDTO>> getAllBookings(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "8") Integer pageSize){
        Page<BookedRoom> bookings = bookingService.getAllBookings(pageNo, pageSize);
        Page<BookingDTO> bookingResponses = bookings.map(this::getBookingResponse);
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/getAllBookingByKeywordAndHotelId/{hotelId}/{keyword}")
    public ResponseEntity<Page<BookingDTO>> getAllBookingByKeywordAndHotelId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "8") Integer pageSize,
                                                           @PathVariable Long hotelId,
                                                           @PathVariable String keyword){
        Page<BookedRoom> bookings = bookingService.getAllBookingByKeywordAndHotelId(pageNo, pageSize, hotelId, keyword);
        Page<BookingDTO> bookingResponses = bookings.map(this::getBookingResponse);
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingDTO bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getBookingByBookingId(@PathVariable Long bookingId){
        try{
            BookedRoom booking = bookingService.findByBookingId(bookingId);
            BookingDTO bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/hotel/{hotelId}/booking")
    public ResponseEntity<Page<BookingDTO>> getBookingByHotelId(@PathVariable Long hotelId,
                                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "8") Integer pageSize){
        Page<BookedRoom> bookings = bookingService.getAllBookingsByHotelId(pageNo, pageSize, hotelId);
        Page<BookingDTO> bookingResponses = bookings.map(this::getBookingResponse);
        return ResponseEntity.ok(bookingResponses);
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest,
                                         @RequestParam(required = false) Long userId,
                                         @RequestParam(required = false) Long redeemedDiscountId){
        try{
            System.out.println("RedeemedDiscountId: " + redeemedDiscountId);
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest, userId, redeemedDiscountId);
            return ResponseEntity.ok(
                    "Room booked successfully, Your booking confirmation code is :" + confirmationCode);

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/booking/{bookingId}/update")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long bookingId,
                                                    @RequestParam String checkInDate,
                                                    @RequestParam String checkOutDate,
                                                    @RequestParam String guestEmail,
                                                    @RequestParam String guestPhoneNumber,
                                                    @RequestParam String guestFullName,
                                                    @RequestParam int totalNumOfGuest){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy,M,d");
        LocalDate checkInLocalDate = LocalDate.parse(checkInDate, dateTimeFormatter);
        LocalDate checkOutLocalDate = LocalDate.parse(checkOutDate, dateTimeFormatter);
        BookedRoom bookedRoom = bookingService.updateBooked(bookingId, checkInLocalDate, checkOutLocalDate,
                guestEmail, guestPhoneNumber, guestFullName, totalNumOfGuest);
        BookingDTO bookingResponse = getBookingResponse(bookedRoom);
        return  ResponseEntity.ok(bookingResponse);
    }

    @GetMapping("/user/{userId}/bookings")
    public ResponseEntity<List<BookingDTO>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookedRoom> bookings = bookingService.getBookingsByUserId(userId);
        List<BookingDTO> bookingResponses = bookings.stream()
                .map(this::getBookingResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingResponses);
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    private BookingDTO getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomDTO room = new RoomDTO(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice(),
                theRoom.getRoomDescription());
        return new BookingDTO(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getBookingConfirmationCode(),
                booking.getGuestEmail(),
                booking.getGuestPhoneNumber(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumOfGuest(),
                booking.getGuestFullName(),
                room);
    }
}
