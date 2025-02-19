package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.hotel.AddHotelRequest;
import com.example.CoutingStarHotel.DTO.request.hotel.UpdateHotelRequest;
import com.example.CoutingStarHotel.DTO.response.hotel.HotelResponse;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.DTO.response.common.ResponseData;
import com.example.CoutingStarHotel.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/{userId}/add")
    public ResponseData<HotelResponse> addHotel(@PathVariable Long userId,
                                                @RequestBody AddHotelRequest request) throws SQLException, IOException {
        var result = hotelService.addHotel(userId, request);
        return ResponseData.<HotelResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<HotelResponse>> getAllHotels(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                  @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = hotelService.getAllHotels(pageNo, pageSize);
        return ResponseData.<PageResponse<HotelResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/search/{keyword}")
    public ResponseData<PageResponse<HotelResponse>> getHotelByKeyword(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                       @RequestParam(defaultValue = "8") Integer pageSize,
                                                                       @PathVariable String keyword) {
        var result = hotelService.getHotelByKeyword(pageNo, pageSize, keyword);
        return ResponseData.<PageResponse<HotelResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/homepage")
    public ResponseData<List<HotelResponse>> getTenFunkyHotelForHomePage() {
        var result = hotelService.getTenFunkyHotelForHomePage();
        return ResponseData.<List<HotelResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/{hotelId}")
    public ResponseData<HotelResponse> getHotelById(@PathVariable Long hotelId) {
        var result = hotelService.getHotelResponseById(hotelId);
        return ResponseData.<HotelResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/city/{city}")
    public ResponseData<PageResponse<HotelResponse>> getHotelsByCity(@PathVariable String city,
                                                                     @RequestParam(defaultValue = "0") Integer pageNo,
                                                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        var result = hotelService.getAllHotelsByCity(city, pageNo, pageSize);
        return ResponseData.<PageResponse<HotelResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/{hotelId}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<HotelResponse> updateHotel(@PathVariable Long hotelId,
                                                   @RequestBody UpdateHotelRequest request) throws IOException, SQLException {
        var result = hotelService.updateHotel(hotelId, request);
        return ResponseData.<HotelResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{hotelId}/softDelete")
    public ResponseData<String> softDeleteHotel(@PathVariable Long hotelId) {
        var result = hotelService.softDelete(hotelId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}

