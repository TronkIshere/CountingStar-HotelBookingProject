package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.response.DashBoardMonthIncreasedResponse;

public interface DashBoardService {
    DashBoardMonthIncreasedResponse getDataForAdminDashBoardMonthIncreased();

    DashBoardMonthIncreasedResponse getDataForHotelOwnerMonthIncreased(Long hotelId);
}
