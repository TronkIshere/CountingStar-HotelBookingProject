package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.response.AdminDashBoardMonthIncreased;
import com.example.CoutingStarHotel.DTO.response.HotelOwnerMonthIncreased;

public interface DashBoardService {
    AdminDashBoardMonthIncreased getDataForAdminDashBoardMonthIncreased();

    HotelOwnerMonthIncreased getDataForHotelOwnerMonthIncreased(Long hotelId);
}
