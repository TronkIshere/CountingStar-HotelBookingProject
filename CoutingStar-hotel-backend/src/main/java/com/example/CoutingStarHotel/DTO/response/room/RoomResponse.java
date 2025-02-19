package com.example.CoutingStarHotel.DTO.response.room;

import com.example.CoutingStarHotel.DTO.response.bookedroom.BookingResponse;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class RoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    private String roomDescription;
    private List<BookingResponse>bookings;
    private double averageNumberOfRoomStars;
}
