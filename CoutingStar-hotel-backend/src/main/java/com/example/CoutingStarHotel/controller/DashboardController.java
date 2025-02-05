package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.response.BarChartResponse;
import com.example.CoutingStarHotel.DTO.response.DashBoardMonthIncreasedResponse;
import com.example.CoutingStarHotel.DTO.response.PieChartResponse;
import com.example.CoutingStarHotel.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final HotelService hotelService;
    private final DashBoardService dashBoardService;
    @GetMapping("/Admin/PieChart")
    public ResponseEntity<List<PieChartResponse>> getDataForAdminPieChart(){
        List<PieChartResponse> PieChartData = hotelService.getNumberOfHotelByEachCity();
        return ResponseEntity.ok(PieChartData);
    }

    @GetMapping("/Admin/BarChart")
    public ResponseEntity<List<BarChartResponse>> getDataForAdminBarChart(){
        List<BarChartResponse> barChartData = hotelService.getHotelRevenueByEachCity();
        return ResponseEntity.ok(barChartData);
    }

    @GetMapping("/HotelOwner/PieChart/{hotelId}")
    public ResponseEntity<List<PieChartResponse>> getDataForAdminPieChartChart(@PathVariable Long hotelId){
        List<PieChartResponse> barChartData = hotelService.getTheRevenceOfEachRoom(hotelId);
        return ResponseEntity.ok(barChartData);
    }

    @GetMapping("/Admin/dashBoardMonthIncreased")
    public ResponseEntity<DashBoardMonthIncreasedResponse> getDataForAdminDashBoardMonthIncreased(){
        DashBoardMonthIncreasedResponse dashBoardMonthIncreasedData = dashBoardService.getDataForAdminDashBoardMonthIncreased();
        return ResponseEntity.ok(dashBoardMonthIncreasedData);
    }

    @GetMapping("/HotelOwner/dashBoardMonthIncreased/{hotelId}")
    public ResponseEntity<DashBoardMonthIncreasedResponse> getDataForHotelOwnerMonthIncreased(@PathVariable Long hotelId){
        DashBoardMonthIncreasedResponse dashBoardMonthIncreasedData = dashBoardService.getDataForHotelOwnerMonthIncreased(hotelId);
        return ResponseEntity.ok(dashBoardMonthIncreasedData);
    }
}
