package com.example.CountingStarHotel.mapper;

import com.example.CountingStarHotel.DTO.response.room.RoomResponse;
import com.example.CountingStarHotel.entities.Room;
import com.example.CountingStarHotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Component
public class RoomMapper {
    private static RoomService roomService;

    @Autowired
    public RoomMapper(@Lazy RoomService roomService) {
        RoomMapper.roomService = roomService;
    }

    public static RoomResponse toRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomPrice(room.getRoomPrice())
                .isBooked(room.isBooked())
                .photo(convertBlobToBase64(room.getPhoto()))
                .roomDescription(room.getRoomDescription())
                .averageNumberOfRoomStars(roomService.averageNumberOfRoomStars(room.getId()))
                .build();
    }


    public static List<RoomResponse> roomResponses(List<Room> rooms) {
        return rooms.stream()
                .map(RoomMapper::toRoomResponse)
                .toList();
    }

    private static String convertBlobToBase64(Blob blob) {
        try {
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (SQLException e) {
            throw new RuntimeException("Error converting Blob to Base64", e);
        }
    }
}
