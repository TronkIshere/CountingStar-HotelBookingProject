package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.Room;
import com.example.CoutingStarHotel.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
public class HotelResponse {
    private Long id;
    private String hotelName;
    private String city;
    private String hotelDescription;
    private String phoneNumber;
    private Blob photo;
    private List<Room> rooms;
    private User user;

    public HotelResponse(Long id, String hotelName, String city, String hotelDescription, String phoneNumber, Blob photo, List<Room> rooms) {
        this.id = id;
        this.hotelName = hotelName;
        this.city = city;
        this.hotelDescription = hotelDescription;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.rooms = rooms;
    }
}
