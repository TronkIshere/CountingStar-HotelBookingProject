package com.example.CoutingStarHotel.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    private String roomDescription;
    private List<BookingResponse>bookings;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, String roomDescription) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
    }

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked,
                        byte[] photoBytes) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        //this.bookings = bookings;
    }

}
