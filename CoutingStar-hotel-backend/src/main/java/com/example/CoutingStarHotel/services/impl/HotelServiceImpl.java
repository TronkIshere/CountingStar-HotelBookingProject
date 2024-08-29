package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.entities.Hotel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface HotelServiceImpl {
    List<Hotel> getAllHotels();


    List<Hotel> getAllHotelsByCity(String city);

    byte[] getHotelPhotobyHotelId(Long hotelId) throws SQLException;

    void deleteHotel(Long hotelId);

    Hotel updateHotel(Long hotelId, String hotelName, String hotelLocation, String hotelDescription, String city, String phoneNumber, MultipartFile photo) throws IOException, SQLException;

    String addHotel(Long userId, String hotelName, String city, String hotelLocation, String hotelDescription, String phoneNumber, MultipartFile photo) throws IOException, SQLException;

    Optional<Hotel> getHotelById(Long hotelId);

    double averageNumberOfHotelStars(Long hotelId);

    Long getHotelLowestPriceByHotelId(Long hotelId);

    Long getHotelHighestPriceByHotelId(Long hotelId);

    List<Hotel> getFiveHotelForHomePage();

    List<PieChartDTO> getNumberOfHotelByEachCity();

    List<BarChartDTO> getHotelRevenueByEachCity();

    int getTotalNumberOfHotels();

    double getPercentageOfHotelsIncreasedDuringTheMonth();
}
