package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    private String roomDescription;
    private List<BookingDTO>bookings;
    private double averageNumberOfRoomStars;

    public RoomDTO(Long id, String roomType, BigDecimal roomPrice, String roomDescription) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
    }

    public RoomDTO(Long id, String roomType, BigDecimal roomPrice, String roomDescription, boolean isBooked,
                   byte[] photoBytes, double averageNumberOfRoomStars) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.averageNumberOfRoomStars = averageNumberOfRoomStars;
    }

}
