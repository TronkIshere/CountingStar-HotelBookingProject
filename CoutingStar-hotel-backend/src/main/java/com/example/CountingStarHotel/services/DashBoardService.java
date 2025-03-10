package com.example.CountingStarHotel.services;

import com.example.CountingStarHotel.DTO.response.dashBoard.AdminDashBoardMonthIncreased;
import com.example.CountingStarHotel.DTO.response.dashBoard.HotelOwnerMonthIncreased;

public interface DashBoardService {
    AdminDashBoardMonthIncreased getDataForAdminDashBoardMonthIncreased();

    HotelOwnerMonthIncreased getDataForHotelOwnerMonthIncreased(Long hotelId);
}
