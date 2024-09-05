package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.DashBoardMonthIncreasedDTO;

public interface DashBoardService {
    DashBoardMonthIncreasedDTO getDataForAdminDashBoardMonthIncreased();

    DashBoardMonthIncreasedDTO getDataForHotelOwnerMonthIncreased(Long hotelId);
}
