package com.example.CoutingStarHotel.DTO.response.dashBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class HotelOwnerMonthIncreased {
    private int totalBookedRoomInSpecificHotel;
    private double percentageOfBookedIncreasedDuringTheMonth;
    private int totalRatingInSpecificHotel;
    private double percentageOfRatingIncreasedDuringTheMonth;
    private BigDecimal totalRevenueInSpecificHotel;
    private double percentageOfRevenueIncreasedDuringTheMonth;
}
