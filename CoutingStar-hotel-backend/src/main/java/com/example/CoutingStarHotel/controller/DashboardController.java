package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.DashBoardMonthIncreasedDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.services.impl.BookingServiceImpl;
import com.example.CoutingStarHotel.services.impl.HotelServiceImpl;
import com.example.CoutingStarHotel.services.impl.RatingServiceImpl;
import com.example.CoutingStarHotel.services.impl.UserServiceImpl;
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
    private final UserServiceImpl userServiceImpl;

    private final HotelServiceImpl hotelServiceImpl;
    private final BookingServiceImpl bookingServiceImpl;
    private final RatingServiceImpl ratingServiceImpl;
    @GetMapping("/PieChart")
    public ResponseEntity<List<PieChartDTO>> getDataForPieChart(){
        List<PieChartDTO> PieChartData = hotelServiceImpl.getNumberOfHotelByEachCity();
        return ResponseEntity.ok(PieChartData);
    }

    @GetMapping("/BarChart")
    public ResponseEntity<List<BarChartDTO>> getDataForBarChart(){
        List<BarChartDTO> barChartData = hotelServiceImpl.getHotelRevenueByEachCity();
        return ResponseEntity.ok(barChartData);
    }

    @GetMapping("/dashBoardMonthIncreased")
    public ResponseEntity<DashBoardMonthIncreasedDTO> getDataForDashBoardMonthIncreased(){
        DashBoardMonthIncreasedDTO dashBoardMonthIncreasedData = new DashBoardMonthIncreasedDTO();
        dashBoardMonthIncreasedData.setTotalNumberOfUsers(userServiceImpl.getTotalNumberOfUsers());
        dashBoardMonthIncreasedData.setPercentageOfUsersIncreasedDuringTheMonth(userServiceImpl.getPercentageOfUsersIncreasedDuringTheMonth());
        dashBoardMonthIncreasedData.setTotalNumberOfHotels(hotelServiceImpl.getTotalNumberOfHotels());
        dashBoardMonthIncreasedData.setPercentageOfHotelsIncreasedDuringTheMonth(hotelServiceImpl.getPercentageOfHotelsIncreasedDuringTheMonth());
        dashBoardMonthIncreasedData.setTotalNumberOfBookedRooms(bookingServiceImpl.getTotalNumberOfBookedRooms());
        dashBoardMonthIncreasedData.setPercentageOfBookedRoomsIncreasedDuringTheMonth(bookingServiceImpl.getPercentageOfBookedRoomsIncreasedDuringTheMonth());
        dashBoardMonthIncreasedData.setTotalNumberOfComments(ratingServiceImpl.getTotalNumberOfComments());
        dashBoardMonthIncreasedData.setPercentageOfCommentsIncreaseDuringTheMonth(ratingServiceImpl.getPercentageOfCommentsIncreaseDuringTheMonth());
        return ResponseEntity.ok(dashBoardMonthIncreasedData);
    }
}
