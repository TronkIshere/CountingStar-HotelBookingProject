package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.DashBoardMonthIncreasedDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
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
    public ResponseEntity<List<PieChartDTO>> getDataForAdminPieChart(){
        List<PieChartDTO> PieChartData = hotelService.getNumberOfHotelByEachCity();
        return ResponseEntity.ok(PieChartData);
    }

    @GetMapping("/Admin/BarChart")
    public ResponseEntity<List<BarChartDTO>> getDataForAdminBarChart(){
        List<BarChartDTO> barChartData = hotelService.getHotelRevenueByEachCity();
        return ResponseEntity.ok(barChartData);
    }

    @GetMapping("/HotelOwner/PieChart/{hotelId}")
    public ResponseEntity<List<PieChartDTO>> getDataForAdminPieChartChart(@PathVariable Long hotelId){
        List<PieChartDTO> barChartData = hotelService.getTheRevenceOfEachRoom(hotelId);
        return ResponseEntity.ok(barChartData);
    }

    @GetMapping("/Admin/dashBoardMonthIncreased")
    public ResponseEntity<DashBoardMonthIncreasedDTO> getDataForAdminDashBoardMonthIncreased(){
        DashBoardMonthIncreasedDTO dashBoardMonthIncreasedData = dashBoardService.getDataForAdminDashBoardMonthIncreased();
        return ResponseEntity.ok(dashBoardMonthIncreasedData);
    }

    @GetMapping("/HotelOwner/dashBoardMonthIncreased/{hotelId}")
    public ResponseEntity<DashBoardMonthIncreasedDTO> getDataForHotelOwnerMonthIncreased(@PathVariable Long hotelId){
        DashBoardMonthIncreasedDTO dashBoardMonthIncreasedData = dashBoardService.getDataForHotelOwnerMonthIncreased(hotelId);
        return ResponseEntity.ok(dashBoardMonthIncreasedData);
    }
}
