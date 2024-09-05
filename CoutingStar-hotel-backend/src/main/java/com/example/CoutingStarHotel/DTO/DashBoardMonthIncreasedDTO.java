package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DashBoardMonthIncreasedDTO {
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

    public DashBoardMonthIncreasedDTO(int totalNumberOfUsers, double percentageOfUsersIncreasedDuringTheMonth, int totalNumberOfHotels, double percentageOfHotelsIncreasedDuringTheMonth, int totalNumberOfBookedRooms, double percentageOfBookedRoomsIncreasedDuringTheMonth, int totalNumberOfComments, double percentageOfCommentsIncreaseDuringTheMonth) {
        this.totalNumberOfUsers = totalNumberOfUsers;
        this.percentageOfUsersIncreasedDuringTheMonth = percentageOfUsersIncreasedDuringTheMonth;
        this.totalNumberOfHotels = totalNumberOfHotels;
        this.percentageOfHotelsIncreasedDuringTheMonth = percentageOfHotelsIncreasedDuringTheMonth;
        this.totalNumberOfBookedRooms = totalNumberOfBookedRooms;
        this.percentageOfBookedRoomsIncreasedDuringTheMonth = percentageOfBookedRoomsIncreasedDuringTheMonth;
        this.totalNumberOfComments = totalNumberOfComments;
        this.percentageOfCommentsIncreaseDuringTheMonth = percentageOfCommentsIncreaseDuringTheMonth;
    }

    public DashBoardMonthIncreasedDTO(int totalBookedRoomInSpecificHotel, double percentageOfBookedIncreasedDuringTheMonth, int totalRatingInSpecificHotel, double percentageOfRatingIncreasedDuringTheMonth, BigDecimal totalRevenueInSpecificHotel, double percentageOfRevenueIncreasedDuringTheMonth) {
        this.totalBookedRoomInSpecificHotel = totalBookedRoomInSpecificHotel;
        this.percentageOfBookedIncreasedDuringTheMonth = percentageOfBookedIncreasedDuringTheMonth;
        this.totalRatingInSpecificHotel = totalRatingInSpecificHotel;
        this.percentageOfRatingIncreasedDuringTheMonth = percentageOfRatingIncreasedDuringTheMonth;
        this.totalRevenueInSpecificHotel = totalRevenueInSpecificHotel;
        this.percentageOfRevenueIncreasedDuringTheMonth = percentageOfRevenueIncreasedDuringTheMonth;
    }
}
