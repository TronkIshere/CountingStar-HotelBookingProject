package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.response.dashBoard.AdminDashBoardMonthIncreased;
import com.example.CoutingStarHotel.DTO.response.dashBoard.HotelOwnerMonthIncreased;

public interface DashBoardService {
    AdminDashBoardMonthIncreased getDataForAdminDashBoardMonthIncreased();

    HotelOwnerMonthIncreased getDataForHotelOwnerMonthIncreased(Long hotelId);
}
