package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.response.dashBoard.AdminDashBoardMonthIncreased;
import com.example.CoutingStarHotel.DTO.response.dashBoard.HotelOwnerMonthIncreased;
import com.example.CoutingStarHotel.services.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashBoardServiceImpl implements DashBoardService {
    UserService userService;
    HotelService hotelService;
    BookingService bookingService;
    RatingService ratingService;
    @Override
    public AdminDashBoardMonthIncreased getDataForAdminDashBoardMonthIncreased() {
        return AdminDashBoardMonthIncreased.builder()
                .totalNumberOfUsers(userService.getTotalNumberOfUsers())
                .percentageOfUsersIncreasedDuringTheMonth(userService.getPercentageOfUsersIncreasedDuringTheMonth())
                .totalNumberOfHotels(hotelService.getTotalNumberOfHotels())
                .percentageOfHotelsIncreasedDuringTheMonth(hotelService.getPercentageOfHotelsIncreasedDuringTheMonth())
                .totalNumberOfBookedRooms(bookingService.getTotalNumberOfBookedRooms())
                .percentageOfBookedRoomsIncreasedDuringTheMonth(bookingService.getPercentageOfBookedRoomsIncreasedDuringTheMonth())
                .totalNumberOfComments(ratingService.getTotalNumberOfComments())
                .percentageOfCommentsIncreaseDuringTheMonth(ratingService.getPercentageOfCommentsIncreaseDuringTheMonth())
                .build();
    }

    @Override
    public HotelOwnerMonthIncreased getDataForHotelOwnerMonthIncreased(Long hotelId) {
        return HotelOwnerMonthIncreased.builder()
                .totalBookedRoomInSpecificHotel(hotelService.getTotalBookedRoomInSpecificHotel(hotelId))
                .percentageOfBookedIncreasedDuringTheMonth(hotelService.getPercentageOfBookedIncreasedDuringTheMonthForHotel(hotelId))
                .totalRatingInSpecificHotel(ratingService.getTotalRatingInSpecificHotel(hotelId))
                .percentageOfRatingIncreasedDuringTheMonth(ratingService.getPercentageOfRatingIncreasedDuringTheMonthForHotel(hotelId))
                .totalRevenueInSpecificHotel(hotelService.getTotalRevenueInSpecificHotel(hotelId))
                .percentageOfRevenueIncreasedDuringTheMonth(hotelService.getPercentageOfRevenueIncreasedDuringTheMonthForHotel(hotelId))
                .build();
    }
}
