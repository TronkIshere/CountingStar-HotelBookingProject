package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.BookedRoom;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Page<BookedRoom> getAllBookings(Integer pageNo, Integer pageSize);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooking(Long roomId, BookedRoom bookingRequest, Long UserId, Long RedeemedDiscountId);

    void cancelBooking(Long bookingId);

    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    List<BookedRoom> getBookingsByUserId(Long userId);

    Page<BookedRoom> getAllBookingsByHotelId(Integer pageNo, Integer pageSize, Long hotelId);

    BookedRoom findByBookingId(Long bookingId);

    BookedRoom updateBooked(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestEmail, String guestPhoneNumber, String guestFullName, int totalNumOfGuest);

    int getTotalNumberOfBookedRooms();

    double getPercentageOfBookedRoomsIncreasedDuringTheMonth();

    Page<BookedRoom> getAllBookingByKeywordAndHotelId(Integer pageNo, Integer pageSize, Long hotelId, String keyword);
}
