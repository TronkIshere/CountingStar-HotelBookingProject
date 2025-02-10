package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.SaveBookingRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateBookedRoom;
import com.example.CoutingStarHotel.DTO.response.BookingResponse;
import com.example.CoutingStarHotel.DTO.response.PageResponse;

public interface BookingService {
    PageResponse<BookingResponse> getAllBookings(Integer pageNo, Integer pageSize);

    BookingResponse findByBookingConfirmationCode(String confirmationCode);

    void cancelBooking(Long bookingId);

    PageResponse<BookingResponse> getBookingsByUserId(Integer pageNo, Integer pageSize, Long userId);

    PageResponse<BookingResponse> getAllBookingsByHotelId(Integer pageNo, Integer pageSize, Long hotelId);

    BookingResponse findByBookingId(Long bookingId);

    BookingResponse updateBooked(Long bookingId, UpdateBookedRoom request);

    int getTotalNumberOfBookedRooms();

    double getPercentageOfBookedRoomsIncreasedDuringTheMonth();

    PageResponse<BookingResponse> getAllBookingByKeywordAndHotelId(Integer pageNo, Integer pageSize, Long hotelId, String keyword);

    BookingResponse saveBooking(Long roomId, SaveBookingRequest request, Long userId, Long redeemedDiscountId);
}
