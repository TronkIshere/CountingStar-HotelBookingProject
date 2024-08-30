package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomServiceImpl {
    Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice, String roomDescription, Long hotelId) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId, String roomType, String roomDescription, BigDecimal roomPrice, MultipartFile photo) throws IOException, SQLException;

    Optional<Room> getRoomById(Long roomId);

    List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    List<Room> getRoomByHotelId(Long hotelId);

    double averageNumberOfRoomStars(Long roomId);
}