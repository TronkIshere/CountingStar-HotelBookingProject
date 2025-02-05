package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.response.RoomResponse;
import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.exception.PhotoRetrievalExcetion;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.services.BookingService;
import com.example.CoutingStarHotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final BookingService bookingService;
    private final RoomRepository roomRepository;

    @PostMapping("/add/new-room/{hotelId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo")MultipartFile photo,
            @RequestParam("roomType")String roomType,
            @RequestParam("roomPrice")BigDecimal roomPrice,
            @RequestParam("roomDescription")String roomDescription,
            @PathVariable Long hotelId) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice, roomDescription, hotelId);
        RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice(), savedRoom.getRoomDescription());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/room/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Page<RoomResponse>> getRoomsByHotelId(@PathVariable Long hotelId,
                                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "8") Integer pageSize) {
        Page<Room> rooms = roomService.getRoomByHotelId(hotelId, pageNo, pageSize);
        Page<RoomResponse> roomResponses = rooms.map(this::getRoomResponse);
        return ResponseEntity.ok(roomResponses);
    }

    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException{
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    @GetMapping("/getAllRoomByKeyword/{hotelId}/{keyword}")
    public ResponseEntity<Page<RoomResponse>> getAllRoomByKeywordAndHotelId(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                            @RequestParam(defaultValue = "8") Integer pageSize,
                                                                            @PathVariable String keyword,
                                                                            @PathVariable Long hotelId) {
        Page<Room> rooms = roomService.getAllRoomByKeywordAndHotelId(pageNo, pageSize, keyword, hotelId);
        Page<RoomResponse> roomResponses = rooms.map(this::getRoomResponse);
        return ResponseEntity.ok(roomResponses);
    }

    @DeleteMapping("/delete/room/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                   @RequestParam("roomType") String roomType,
                                                   @RequestParam("roomDescription") String roomDescription,
                                                   @RequestParam("roomPrice") BigDecimal roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {
        Room theRoom = roomService.updateRoom(roomId, roomType, roomDescription, roomPrice, photo);
        RoomResponse roomResponse = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
        Optional<Room> theRoom = roomService.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomResponse roomResponse = getRoomResponse(room);
            return  ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    private List<BookedRoom> getAllBookingByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
            @RequestParam("roomType") String roomType) throws SQLException {
        List<Room> availableRooms = roomService.getAvailableRooms(checkInDate, checkOutDate, roomType);
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : availableRooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0){
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(photoBase64);
                roomResponses.add(roomResponse);
            }
        }
        if(roomResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(roomResponses);
        }
    }

    private RoomResponse getRoomResponse(Room room) {
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalExcetion("Error retrieving photo");
            }
        }

        double averageNumberOfRoomStars = roomService.averageNumberOfRoomStars(room.getId());

        return new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.getRoomDescription(),
                room.isBooked(),
                photoBytes,
                averageNumberOfRoomStars);
    }
}


