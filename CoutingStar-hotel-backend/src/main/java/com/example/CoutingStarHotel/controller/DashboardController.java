package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.DashBoardMonthIncreasedDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.services.BookingService;
import com.example.CoutingStarHotel.services.HotelService;
import com.example.CoutingStarHotel.services.RatingService;
import com.example.CoutingStarHotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final UserService userService;
    private final HotelService hotelService;
    private final BookingService bookingService;
    private final RatingService ratingService;
    @GetMapping("/PieChart")
    public ResponseEntity<List<PieChartDTO>> getDataForPieChart(){
        List<PieChartDTO> PieChartData = hotelService.getNumberOfHotelByEachCity();
        return ResponseEntity.ok(PieChartData);
    }

    @GetMapping("/BarChart")
    public ResponseEntity<List<BarChartDTO>> getDataForBarChart(){
        List<BarChartDTO> barChartData = hotelService.getHotelRevenueByEachCity();
        return ResponseEntity.ok(barChartData);
    }

    @GetMapping("/dashBoardMonthIncreased")
    public ResponseEntity<DashBoardMonthIncreasedDTO> getDataForDashBoardMonthIncreased(){
        DashBoardMonthIncreasedDTO dashBoardMonthIncreasedData = new DashBoardMonthIncreasedDTO();
        dashBoardMonthIncreasedData.setTotalNumberOfUsers(userService.getTotalNumberOfUsers());
        dashBoardMonthIncreasedData.setPercentageOfUsersIncreasedDuringTheMonth(userService.getPercentageOfUsersIncreasedDuringTheMonth());
        dashBoardMonthIncreasedData.setTotalNumberOfHotels(hotelService.getTotalNumberOfHotels());
        dashBoardMonthIncreasedData.setPercentageOfHotelsIncreasedDuringTheMonth(hotelService.getPercentageOfHotelsIncreasedDuringTheMonth());
        dashBoardMonthIncreasedData.setTotalNumberOfBookedRooms(bookingService.getTotalNumberOfBookedRooms());
        dashBoardMonthIncreasedData.setPercentageOfBookedRoomsIncreasedDuringTheMonth(bookingService.getPercentageOfBookedRoomsIncreasedDuringTheMonth());
        dashBoardMonthIncreasedData.setTotalNumberOfComments(ratingService.getTotalNumberOfComments());
        dashBoardMonthIncreasedData.setPercentageOfCommentsIncreaseDuringTheMonth(ratingService.getPercentageOfCommentsIncreaseDuringTheMonth());
        return ResponseEntity.ok(dashBoardMonthIncreasedData);
    }
}
