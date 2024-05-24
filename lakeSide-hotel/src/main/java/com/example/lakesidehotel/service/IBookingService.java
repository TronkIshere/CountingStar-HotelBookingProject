package com.example.lakesidehotel.service;

import com.example.lakesidehotel.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookings();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);

    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    List<BookedRoom> getBookingsByUserId(Long userId);
}
