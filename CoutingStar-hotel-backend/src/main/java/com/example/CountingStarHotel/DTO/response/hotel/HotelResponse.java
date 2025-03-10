package com.example.CountingStarHotel.DTO.response.hotel;

import com.example.CountingStarHotel.entities.Room;
import com.example.CountingStarHotel.entities.User;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class HotelResponse implements Serializable {
    private Long id;
    private String hotelName;
    private String city;
    private String hotelLocation;
    private String hotelDescription;
    private String phoneNumber;
    private String photo;
    private List<Room> rooms;
    private User user;
    private double averageNumberOfHotelStars;
    private Long lowestPrice;
    private Long highestPrice;
}
