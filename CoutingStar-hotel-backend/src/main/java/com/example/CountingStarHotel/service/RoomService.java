package com.example.CountingStarHotel.service;

import com.example.CountingStarHotel.DTO.request.room.AddRoomRequest;
import com.example.CountingStarHotel.DTO.request.room.UpdateRoomRequest;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.DTO.response.room.RoomResponse;
import com.example.CountingStarHotel.entity.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface RoomService {
    RoomResponse addNewRoom(AddRoomRequest request, Long hotelId) throws SQLException, IOException;

    List<RoomResponse> getAllRooms();

    void deleteRoom(Long roomId);

    RoomResponse updateRoom(Long roomId, UpdateRoomRequest request) throws IOException, SQLException;

    Room getRoomById(Long roomId);

    RoomResponse getRoomResponseById(Long roomId);

    PageResponse<RoomResponse> getRoomByHotelId(Long hotelId, Integer pageNo, Integer pageSize);

    double averageNumberOfRoomStars(Long roomId);

    PageResponse<RoomResponse> getAllRoomByKeywordAndHotelId(Integer pageNo, Integer pageSize, String keyword, Long hotelId);

    String softDelete(Long roomId);
}
