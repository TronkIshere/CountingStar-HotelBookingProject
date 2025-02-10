package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.AddRoomRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateRoomRequest;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.DTO.response.ResponseData;
import com.example.CoutingStarHotel.DTO.response.RoomResponse;
import com.example.CoutingStarHotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/add/new-room/{hotelId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<RoomResponse> addNewRoom(
            @RequestParam AddRoomRequest request,
            @PathVariable Long hotelId) throws SQLException, IOException {
        var result = roomService.addNewRoom(request, hotelId);
        return ResponseData.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/{hotelId}")
    public ResponseData<PageResponse<RoomResponse>> getRoomsByHotelId(@PathVariable Long hotelId,
                                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                                      @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = roomService.getRoomByHotelId(hotelId, pageNo, pageSize);
        return ResponseData.<PageResponse<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/all-rooms")
    public ResponseData<List<RoomResponse>> getAllRooms() {
        var result = roomService.getAllRooms();
        return ResponseData.<List<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/getAllRoomByKeyword/{hotelId}/{keyword}")
    public ResponseData<PageResponse<RoomResponse>> getAllRoomByKeywordAndHotelId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                  @RequestParam(defaultValue = "8") Integer pageSize,
                                                                                  @PathVariable String keyword,
                                                                                  @PathVariable Long hotelId) {
        var result = roomService.getAllRoomByKeywordAndHotelId(pageNo, pageSize, keyword, hotelId);
        return ResponseData.<PageResponse<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/delete/room/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                 @RequestParam UpdateRoomRequest request) throws SQLException, IOException {
        var result = roomService.updateRoom(roomId, request);
        return ResponseData.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/room/{roomId}")
    public ResponseData<RoomResponse> getRoomById(@PathVariable Long roomId) {
        var result = roomService.getRoomResponseById(roomId);
        return ResponseData.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}


