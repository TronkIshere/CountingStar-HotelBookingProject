package com.example.CoutingStarHotel.mapper;

import com.example.CoutingStarHotel.DTO.response.hotel.HotelResponse;
import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.services.HotelService;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Component
public class HotelMapper {
    private static HotelService hotelService;

    public HotelMapper(HotelService hotelService) {
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
