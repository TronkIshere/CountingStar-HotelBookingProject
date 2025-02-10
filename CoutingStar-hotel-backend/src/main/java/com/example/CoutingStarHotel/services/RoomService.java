package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.AddRoomRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateRoomRequest;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.DTO.response.ResponseData;
import com.example.CoutingStarHotel.DTO.response.RoomResponse;
import com.example.CoutingStarHotel.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    RoomResponse addNewRoom(AddRoomRequest request, Long hotelId) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<RoomResponse> getAllRooms();

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

    void deleteRoom(Long roomId);

    RoomResponse updateRoom(Long roomId, UpdateRoomRequest request) throws IOException, SQLException;

    Room getRoomById(Long roomId);

    List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    PageResponse<RoomResponse> getRoomByHotelId(Long hotelId, Integer pageNo, Integer pageSize);

    double averageNumberOfRoomStars(Long roomId);

    PageResponse<RoomResponse> getAllRoomByKeywordAndHotelId(Integer pageNo, Integer pageSize, String keyword, Long hotelId);
}
