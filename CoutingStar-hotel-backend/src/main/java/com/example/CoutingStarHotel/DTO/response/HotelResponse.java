package com.example.CoutingStarHotel.DTO.response;

import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.entities.User;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

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
