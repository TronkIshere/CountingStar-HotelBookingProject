package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.DashBoardMonthIncreasedDTO;
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
    public DashBoardMonthIncreasedDTO getDataForAdminDashBoardMonthIncreased() {
        DashBoardMonthIncreasedDTO dashBoardMonthIncreasedData = new DashBoardMonthIncreasedDTO(
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
    public DashBoardMonthIncreasedDTO getDataForHotelOwnerMonthIncreased(Long hotelId) {
        DashBoardMonthIncreasedDTO dashBoardMonthIncreasedData = new DashBoardMonthIncreasedDTO(
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
