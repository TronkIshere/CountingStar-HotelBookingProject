package com.example.CountingStarHotel.controller;

import com.example.CountingStarHotel.DTO.response.common.ResponseData;
import com.example.CountingStarHotel.DTO.response.dashBoard.AdminDashBoardMonthIncreased;
import com.example.CountingStarHotel.DTO.response.dashBoard.BarChartResponse;
import com.example.CountingStarHotel.DTO.response.dashBoard.HotelOwnerMonthIncreased;
import com.example.CountingStarHotel.DTO.response.dashBoard.PieChartResponse;
import com.example.CountingStarHotel.service.DashBoardService;
import com.example.CountingStarHotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseData<List<PieChartResponse>> getDataForAdminPieChart(){
        var result = hotelService.getNumberOfHotelByEachCity();
        return ResponseData.<List<PieChartResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/Admin/BarChart")
    public ResponseData<List<BarChartResponse>> getDataForAdminBarChart(){
        var result = hotelService.getHotelRevenueByEachCity();
        return ResponseData.<List<BarChartResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/HotelOwner/PieChart/{hotelId}")
    public ResponseData<List<PieChartResponse>> getDataForAdminPieChartChart(@PathVariable Long hotelId){
        var result = hotelService.getTheRevenueOfEachRoom(hotelId);
        return ResponseData.<List<PieChartResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/Admin/dashBoardMonthIncreased")
    public ResponseData<AdminDashBoardMonthIncreased> getDataForAdminDashBoardMonthIncreased(){
        var result = dashBoardService.getDataForAdminDashBoardMonthIncreased();
        return ResponseData.<AdminDashBoardMonthIncreased>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/HotelOwner/dashBoardMonthIncreased/{hotelId}")
    public ResponseData<HotelOwnerMonthIncreased> getDataForHotelOwnerMonthIncreased(@PathVariable Long hotelId){
        var result = dashBoardService.getDataForHotelOwnerMonthIncreased(hotelId);
        return ResponseData.<HotelOwnerMonthIncreased>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
