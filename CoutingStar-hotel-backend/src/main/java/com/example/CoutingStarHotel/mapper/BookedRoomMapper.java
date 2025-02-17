package com.example.CoutingStarHotel.mapper;

import com.example.CoutingStarHotel.DTO.response.BookingResponse;
import com.example.CoutingStarHotel.entities.BookedRoom;

import java.util.List;

public class BookedRoomMapper {
    private BookedRoomMapper() {}

    public static BookingResponse toBookingResponse(BookedRoom bookedRoom) {
        return BookingResponse.builder()
                .id(bookedRoom.getId())
                .checkInDate(bookedRoom.getCheckOutDate())
                .guestPhoneNumber(bookedRoom.getGuestPhoneNumber())
                .guestFullName(bookedRoom.getGuestFullName())
                .guestEmail(bookedRoom.getGuestEmail())
                .numOfAdults(bookedRoom.getNumOfAdults())
                .numOfChildren(bookedRoom.getNumOfChildren())
                .totalNumOfGuest(bookedRoom.getTotalNumOfGuest())
                .bookingConfirmationCode(bookedRoom.getBookingConfirmationCode())
                .bookingDay(bookedRoom.getBookingDay())
                .totalAmount(bookedRoom.getTotalAmount())
                .isCancelled(bookedRoom.getIsCancelled())
                .build();
    }

    public static List<BookingResponse> bookingResponses(List<BookedRoom> bookedRooms) {
        return bookedRooms.stream()
                .map(BookedRoomMapper::toBookingResponse)
                .toList();
    }
}

