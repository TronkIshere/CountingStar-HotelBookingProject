package com.example.lakesidehotel.service;

import com.example.lakesidehotel.model.BookedRoom;

import java.util.List;
import java.util.Optional;

public interface IBookingService {
    List<BookedRoom> getAllBookings();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);
}
