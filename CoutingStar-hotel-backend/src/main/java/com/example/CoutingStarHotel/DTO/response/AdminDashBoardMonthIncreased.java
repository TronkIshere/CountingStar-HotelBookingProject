package com.example.CoutingStarHotel.DTO.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminDashBoardMonthIncreased {
    private int totalNumberOfUsers;
    private double percentageOfUsersIncreasedDuringTheMonth;
    private int totalNumberOfHotels;
    private double percentageOfHotelsIncreasedDuringTheMonth;
    private int totalNumberOfBookedRooms;
    private double percentageOfBookedRoomsIncreasedDuringTheMonth;
    private int totalNumberOfComments;
    private double percentageOfCommentsIncreaseDuringTheMonth;
}
