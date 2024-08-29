package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.BookedRoom;

import java.time.LocalDate;
import java.util.List;

public interface BookingServiceImpl {
    List<BookedRoom> getAllBookings();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest, Long UserId);

    void cancelBooking(Long bookingId);

    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    List<BookedRoom> getBookingsByUserId(Long userId);

    List<BookedRoom> getAllBookingsByHotelId(Long hotelId);

    BookedRoom findByBookingId(Long bookingId);

    BookedRoom updateBooked(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestEmail, String guestPhoneNumber, String guestFullName, int totalNumOfGuest);

    int getTotalNumberOfBookedRooms();

    double getPercentageOfBookedRoomsIncreasedDuringTheMonth();
}
