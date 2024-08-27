package com.example.CoutingStarHotel.DTO;

import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.List;

@Data
@NoArgsConstructor
public class HotelDTO {
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
    public HotelDTO(Long id, String hotelName, String city, String hotelLocation, String hotelDescription, String phoneNumber, byte[] photoBytes) {
        this.id = id;
        this.hotelName = hotelName;
        this.city = city;
        this.hotelLocation = hotelLocation;
        this.hotelDescription = hotelDescription;
        this.phoneNumber = phoneNumber;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
    }

    public HotelDTO(Long id, String hotelName, String city, String hotelLocation, String hotelDescription, String phoneNumber, byte[] photoBytes, double averageNumberOfHotelStars, Long lowestPrice, Long highestPrice) {
        this.id = id;
        this.hotelName = hotelName;
        this.city = city;
        this.hotelLocation = hotelLocation;
        this.hotelDescription = hotelDescription;
        this.phoneNumber = phoneNumber;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.averageNumberOfHotelStars = averageNumberOfHotelStars;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }
}
