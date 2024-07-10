package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.BookedRoom;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookings();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest, Long UserId);

    void cancelBooking(Long bookingId);

    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    List<BookedRoom> getBookingsByUserId(Long userId);

    List<BookedRoom> getAllBookingsByHotelId(Long hotelId);

    BookedRoom findByBookingId(Long bookingId);

    BookedRoom updateBooked(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestEmail, String guestPhoneNumber, String guestFullName, int totalNumOfGuest);
}
