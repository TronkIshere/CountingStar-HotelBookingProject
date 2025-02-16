package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.request.AddRoomRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateRoomRequest;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.DTO.response.RoomResponse;
import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.entities.Rating;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.mapper.RoomMapper;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.services.RoomService;
import com.example.CoutingStarHotel.services.impl.helpers.HotelCoordinator;
import com.example.CoutingStarHotel.services.impl.helpers.RatingCoordinator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelCoordinator hotelCoordinator;
    private final RatingCoordinator ratingCoordinator;

    @Override
    public RoomResponse addNewRoom(AddRoomRequest request, Long hotelId) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setRoomPrice(request.getRoomPrice());
        room.setRoomDescription(request.getRoomDescription());
        Hotel hotel = hotelCoordinator.getHotelById(hotelId);
        Blob photoBlob = processPhoto(request.getPhoto());
        if (photoBlob != null) {
            room.setPhoto(photoBlob);
        }
        hotel.addRoom(room);
        roomRepository.save(room);
        return RoomMapper.toRoomResponse(room);
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();
        return RoomMapper.roomResponses(roomList);
    }


    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if (theRoom.isPresent()) {
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public RoomResponse updateRoom(Long roomId, UpdateRoomRequest request) throws IOException, SQLException {
        Room room = getRoomById(roomId);
        room.setRoomType(request.getRoomType());
        room.setRoomDescription(request.getRoomDescription());
        room.setRoomPrice(request.getRoomPrice());

        Blob photoBlob = processPhoto(request.getPhoto());
        if (photoBlob != null) {
            room.setPhoto(photoBlob);
        }

        roomRepository.save(room);
        return RoomMapper.toRoomResponse(room);
    }

    @Override
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + roomId));
    }


    @Override
    public RoomResponse getRoomResponseById(Long roomId) {
        return RoomMapper.toRoomResponse(getRoomById(roomId));
    }

    @Override
    public PageResponse<RoomResponse> getRoomByHotelId(Long hotelId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Room> roomPage = roomRepository.findRoomsByHotelId(hotelId, pageable);

        List<Room> roomList = roomPage.getContent();

        return PageResponse.<RoomResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(roomPage.getTotalPages())
                .totalElements(roomPage.getTotalElements())
                .data(RoomMapper.roomResponses(roomList))
                .build();
    }

    @Override
    public double averageNumberOfRoomStars(Long roomId) {
        double result = 0;
        int count = 0;
        List<Rating> ratings = ratingCoordinator.getAllRatingByRoomId(roomId);
        for (Rating rating : ratings) {
            result += rating.getStar();
            count++;
        }
        return result / count;
    }

    @Override
    public PageResponse<RoomResponse> getAllRoomByKeywordAndHotelId(Integer pageNo, Integer pageSize, String keyword, Long hotelId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Room> roomPage = roomRepository.getAllRoomByKeywordAndHotelId(pageable, keyword, hotelId);

        List<Room> roomList = roomPage.getContent();

        return PageResponse.<RoomResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(roomPage.getTotalPages())
                .totalElements(roomPage.getTotalElements())
                .data(RoomMapper.roomResponses(roomList))
                .build();
    }

    private Blob processPhoto(MultipartFile photo) throws IOException, SQLException {
        if (photo != null && !photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            return new SerialBlob(photoBytes);
        }
        return null;
    }
}
