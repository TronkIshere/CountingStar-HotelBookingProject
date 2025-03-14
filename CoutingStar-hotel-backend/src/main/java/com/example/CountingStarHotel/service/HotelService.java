package com.example.CountingStarHotel.service;

import com.example.CountingStarHotel.DTO.request.hotel.AddHotelRequest;
import com.example.CountingStarHotel.DTO.request.hotel.UpdateHotelRequest;
import com.example.CountingStarHotel.DTO.response.dashBoard.BarChartResponse;
import com.example.CountingStarHotel.DTO.response.hotel.HotelResponse;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.DTO.response.dashBoard.PieChartResponse;
import com.example.CountingStarHotel.entity.Hotel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface HotelService {
    PageResponse<HotelResponse> getAllHotels(Integer pageNo, Integer pageSize);

    PageResponse<HotelResponse> getAllHotelsByCity(String city, Integer pageNo, Integer pageSize);

    void deleteHotel(Long hotelId);

    HotelResponse updateHotel(Long hotelId, UpdateHotelRequest request) throws IOException, SQLException;

    HotelResponse addHotel(Long userId, AddHotelRequest request) throws IOException, SQLException;

    Hotel getHotelById(Long hotelId);

    double averageNumberOfHotelStars(Long hotelId);

    Long getHotelLowestPriceByHotelId(Long hotelId);

    Long getHotelHighestPriceByHotelId(Long hotelId);

    List<HotelResponse> getTenFunkyHotelForHomePage();

    List<PieChartResponse> getNumberOfHotelByEachCity();

    List<BarChartResponse> getHotelRevenueByEachCity();

    int getTotalNumberOfHotels();

    double getPercentageOfHotelsIncreasedDuringTheMonth();

    List<PieChartResponse> getTheRevenueOfEachRoom(Long hotelId);

    BigDecimal getTotalRevenueInSpecificHotel(Long hotelId);

    double getPercentageOfRevenueIncreasedDuringTheMonthForHotel(Long hotelId);

    int getTotalBookedRoomInSpecificHotel(Long hotelId);

    double getPercentageOfBookedIncreasedDuringTheMonthForHotel(Long hotelId);

    PageResponse<HotelResponse> getHotelByKeyword(Integer pageNo, Integer pageSize, String keyword);

    HotelResponse getHotelResponseById(Long hotelId);

    String softDelete(Long hotelId);
}
