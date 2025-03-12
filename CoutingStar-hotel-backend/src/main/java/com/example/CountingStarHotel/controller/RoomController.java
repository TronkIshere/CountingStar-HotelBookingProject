package com.example.CountingStarHotel.controller;

import com.example.CountingStarHotel.DTO.request.room.AddRoomRequest;
import com.example.CountingStarHotel.DTO.request.room.UpdateRoomRequest;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.DTO.response.common.ResponseData;
import com.example.CountingStarHotel.DTO.response.room.RoomResponse;
import com.example.CountingStarHotel.service.RoomService;
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

    @PostMapping("/hotels/{hotelId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<RoomResponse> addNewRoom(
            @RequestBody AddRoomRequest request,
            @PathVariable Long hotelId) throws SQLException, IOException {
        var result = roomService.addNewRoom(request, hotelId);
        return ResponseData.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotels/{hotelId}")
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

    @GetMapping
    public ResponseData<List<RoomResponse>> getAllRooms() {
        var result = roomService.getAllRooms();
        return ResponseData.<List<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotels/{hotelId}/search")
    public ResponseData<PageResponse<RoomResponse>> getAllRoomByKeywordAndHotelId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                  @RequestParam(defaultValue = "8") Integer pageSize,
                                                                                  @RequestParam String keyword,
                                                                                  @PathVariable Long hotelId) {
        var result = roomService.getAllRoomByKeywordAndHotelId(pageNo, pageSize, keyword, hotelId);
        return ResponseData.<PageResponse<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                 @RequestBody UpdateRoomRequest request) throws SQLException, IOException {
        var result = roomService.updateRoom(roomId, request);
        return ResponseData.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/{roomId}")
    public ResponseData<RoomResponse> getRoomById(@PathVariable Long roomId) {
        var result = roomService.getRoomResponseById(roomId);
        return ResponseData.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/{roomId}/soft-delete")
    public ResponseData<String> softDelete(@PathVariable Long roomId) {
        var result = roomService.softDelete(roomId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}



