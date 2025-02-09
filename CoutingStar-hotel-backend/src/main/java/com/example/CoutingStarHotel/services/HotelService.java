package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.AddHotelRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateHotelRequest;
import com.example.CoutingStarHotel.DTO.response.*;
import com.example.CoutingStarHotel.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface HotelService {
    PageResponse<HotelResponse> getAllHotels(Integer pageNo, Integer pageSize);

    PageResponse<HotelResponse> getAllHotelsByCity(String city, Integer pageNo, Integer pageSize);

    void deleteHotel(Long hotelId);

    byte[] getHotelPhotobyHotelId(Long hotelId) throws SQLException;

    HotelResponse updateHotel(Long hotelId, UpdateHotelRequest request) throws IOException, SQLException;

    HotelResponse addHotel(Long userId, AddHotelRequest request) throws IOException, SQLException;

    HotelResponse getHotelById(Long hotelId);

    double averageNumberOfHotelStars(Long hotelId);

    Long getHotelLowestPriceByHotelId(Long hotelId);

    Long getHotelHighestPriceByHotelId(Long hotelId);

    List<HotelResponse> getTenFunkyHotelForHomePage();

    List<PieChartResponse> getNumberOfHotelByEachCity();

    List<BarChartResponse> getHotelRevenueByEachCity();

    int getTotalNumberOfHotels();

    double getPercentageOfHotelsIncreasedDuringTheMonth();

    List<PieChartResponse> getTheRevenceOfEachRoom(Long hotelId);

    BigDecimal getTotalRevenueInSpecificHotel(Long hotelId);

    double getPercentageOfRevenueIncreasedDuringTheMonthForHotel(Long hotelId);

    int getTotalBookedRoomInSpecificHotel(Long hotelId);

    double getPercentageOfBookedIncreasedDuringTheMonthForHotel(Long hotelId);

    PageResponse<HotelResponse> getHotelByKeyword(Integer pageNo, Integer pageSize, String keyword);
}
