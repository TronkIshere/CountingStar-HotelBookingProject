package com.example.CoutingStarHotel.DTO.response.dashBoard;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class DashBoardMonthIncreasedResponse implements Serializable {
    private int totalNumberOfUsers;
    private double percentageOfUsersIncreasedDuringTheMonth;
    private int totalNumberOfHotels;
    private double percentageOfHotelsIncreasedDuringTheMonth;
    private int totalNumberOfBookedRooms;
    private double percentageOfBookedRoomsIncreasedDuringTheMonth;
    private int totalNumberOfComments;
    private double percentageOfCommentsIncreaseDuringTheMonth;

    private int totalBookedRoomInSpecificHotel;
    private double percentageOfBookedIncreasedDuringTheMonth;
    private int totalRatingInSpecificHotel;
    private double percentageOfRatingIncreasedDuringTheMonth;
    private BigDecimal totalRevenueInSpecificHotel;
    private double percentageOfRevenueIncreasedDuringTheMonth;
}
