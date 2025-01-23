package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface HotelService {
    Page<Hotel> getAllHotels(Integer pageNo, Integer pageSize);

    Page<Hotel> getAllHotelsByCity(String city, Integer pageNo, Integer pageSize);

    void deleteHotel(Long hotelId);

    byte[] getHotelPhotobyHotelId(Long hotelId) throws SQLException;

    Hotel updateHotel(Long hotelId, String hotelName, String hotelLocation, String hotelDescription, String city, String phoneNumber, MultipartFile photo) throws IOException, SQLException;

    String addHotel(Long userId, String hotelName, String city, String hotelLocation, String hotelDescription, String phoneNumber, MultipartFile photo) throws IOException, SQLException;

    Optional<Hotel> getHotelById(Long hotelId);

    double averageNumberOfHotelStars(Long hotelId);

    Long getHotelLowestPriceByHotelId(Long hotelId);

    Long getHotelHighestPriceByHotelId(Long hotelId);

    List<Hotel> getTenFunkyHotelForHomePage();

    List<PieChartDTO> getNumberOfHotelByEachCity();

    List<BarChartDTO> getHotelRevenueByEachCity();

    int getTotalNumberOfHotels();

    double getPercentageOfHotelsIncreasedDuringTheMonth();

    List<PieChartDTO> getTheRevenceOfEachRoom(Long hotelId);

    BigDecimal getTotalRevenueInSpecificHotel(Long hotelId);

    double getPercentageOfRevenueIncreasedDuringTheMonthForHotel(Long hotelId);

    int getTotalBookedRoomInSpecificHotel(Long hotelId);

    double getPercentageOfBookedIncreasedDuringTheMonthForHotel(Long hotelId);

    Page<Hotel> getHotelByKeyword(Integer pageNo, Integer pageSize, String keyword);
}
