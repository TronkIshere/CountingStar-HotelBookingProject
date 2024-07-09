package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.Room;
import com.example.CoutingStarHotel.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
public class HotelResponse {
    private Long id;
    private String hotelName;
    private String city;
    private String hotelLocation;
    private String hotelDescription;
    private String phoneNumber;
    private String photo;
    private List<Room> rooms;
    private User user;

    public HotelResponse(Long id, String hotelName, String city, String hotelLocation, String hotelDescription, String phoneNumber, byte[] photoBytes) {
        this.id = id;
        this.hotelName = hotelName;
        this.city = city;
        this.hotelLocation = hotelLocation;
        this.hotelDescription = hotelDescription;
        this.phoneNumber = phoneNumber;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
    }
}
