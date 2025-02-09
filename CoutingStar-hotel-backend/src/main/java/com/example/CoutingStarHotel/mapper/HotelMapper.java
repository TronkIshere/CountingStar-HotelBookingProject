package com.example.CoutingStarHotel.mapper;

import com.example.CoutingStarHotel.DTO.response.HotelResponse;
import com.example.CoutingStarHotel.DTO.response.RatingResponse;
import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Service
public class HotelMapper {
    private static HotelService hotelService;

    @Autowired
    public HotelMapper(@Lazy HotelService hotelService) {
        HotelMapper.hotelService = hotelService;
    }

    public static HotelResponse toHotelResponse(Hotel hotel) {
        Long lowestPrice
                = hotelService.getHotelLowestPriceByHotelId(hotel.getId())
                != null ? hotelService.getHotelLowestPriceByHotelId(hotel.getId()) : 0;
        Long highestPrice
                = hotelService.getHotelHighestPriceByHotelId(hotel.getId())
                != null ? hotelService.getHotelHighestPriceByHotelId(hotel.getId()) : 0;

        return HotelResponse.builder()
                .id(hotel.getId())
                .hotelName(hotel.getHotelName())
                .city(hotel.getCity())
                .hotelLocation(hotel.getHotelLocation())
                .hotelDescription(hotel.getHotelDescription())
                .phoneNumber(hotel.getPhoneNumber())
                .photo(convertBlobToBase64(hotel.getPhoto()))
                .averageNumberOfHotelStars(hotelService.averageNumberOfHotelStars(hotel.getId()))
                .lowestPrice(lowestPrice)
                .highestPrice(highestPrice)
                .build();
    }

    public static List<HotelResponse> hotelResponses(List<Hotel> hotels) {
        return hotels.stream()
                .map(HotelMapper::toHotelResponse)
                .toList();
    }

    private static String convertBlobToBase64(Blob blob) {
        if (blob == null) {
            return null;
        }
        try {
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (SQLException e) {
            throw new RuntimeException("Error converting Blob to Base64", e);
        }
    }
}
