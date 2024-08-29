package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

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
}
