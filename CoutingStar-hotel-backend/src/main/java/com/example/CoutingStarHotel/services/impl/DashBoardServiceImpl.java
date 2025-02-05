package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.response.DashBoardMonthIncreasedResponse;
import com.example.CoutingStarHotel.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final UserService userService;
    private final HotelService hotelService;
    private final BookingService bookingService;
    private final RatingService ratingService;
    @Override
    public DashBoardMonthIncreasedResponse getDataForAdminDashBoardMonthIncreased() {
        DashBoardMonthIncreasedResponse dashBoardMonthIncreasedData = new DashBoardMonthIncreasedResponse(
                userService.getTotalNumberOfUsers(),
                userService.getPercentageOfUsersIncreasedDuringTheMonth(),
                hotelService.getTotalNumberOfHotels(),
                hotelService.getPercentageOfHotelsIncreasedDuringTheMonth(),
                bookingService.getTotalNumberOfBookedRooms(),
                bookingService.getPercentageOfBookedRoomsIncreasedDuringTheMonth(),
                ratingService.getTotalNumberOfComments(),
                ratingService.getPercentageOfCommentsIncreaseDuringTheMonth()
        );
        return dashBoardMonthIncreasedData;
    }

    @Override
    public DashBoardMonthIncreasedResponse getDataForHotelOwnerMonthIncreased(Long hotelId) {
        DashBoardMonthIncreasedResponse dashBoardMonthIncreasedData = new DashBoardMonthIncreasedResponse(
                hotelService.getTotalBookedRoomInSpecificHotel(hotelId),
                hotelService.getPercentageOfBookedIncreasedDuringTheMonthForHotel(hotelId),
                ratingService.getTotalRatingInSpecificHotel(hotelId),
                ratingService.getPercentageOfRatingIncreasedDuringTheMonthForHotel(hotelId),
                hotelService.getTotalRevenueInSpecificHotel(hotelId),
                hotelService.getPercentageOfRevenueIncreasedDuringTheMonthForHotel(hotelId)
        );
        return dashBoardMonthIncreasedData;
    }
}
