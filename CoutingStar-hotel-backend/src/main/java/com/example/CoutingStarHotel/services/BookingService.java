package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.bookedroom.SaveBookingRequest;
import com.example.CoutingStarHotel.DTO.request.bookedroom.UpdateBookedRoom;
import com.example.CoutingStarHotel.DTO.response.bookedroom.BookingResponse;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.entities.BookedRoom;

public interface BookingService {
    PageResponse<BookingResponse> getAllBookings(Integer pageNo, Integer pageSize);

    BookingResponse findByBookingConfirmationCode(String confirmationCode);

    void cancelBooking(Long bookingId);

    PageResponse<BookingResponse> getBookingsByUserId(Integer pageNo, Integer pageSize, Long userId);

    PageResponse<BookingResponse> getAllBookingsByHotelId(Integer pageNo, Integer pageSize, Long hotelId);

    BookingResponse findBookingResponseById(Long bookingId);

    BookedRoom findBookingById(Long id);

    BookingResponse updateBooked(Long bookingId, UpdateBookedRoom request);

    int getTotalNumberOfBookedRooms();

    double getPercentageOfBookedRoomsIncreasedDuringTheMonth();

    PageResponse<BookingResponse> getAllBookingByKeywordAndHotelId(Integer pageNo, Integer pageSize, Long hotelId, String keyword);

    BookingResponse saveBooking(Long roomId, SaveBookingRequest request, Long userId, Long redeemedDiscountId);

    BookedRoom findRoomUserHasBookedAndNotComment(Long hotelId, Long UserId);

    String softDelete(Long bookingId);
}
