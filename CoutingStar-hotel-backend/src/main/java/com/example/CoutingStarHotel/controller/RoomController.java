package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.PhotoRetrievalExcetion;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.DTO.RoomDTO;
import com.example.CoutingStarHotel.services.impl.BookingServiceImpl;
import com.example.CoutingStarHotel.services.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
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
    private final RoomServiceImpl roomServiceImpl;
    private final BookingServiceImpl bookingServiceImpl;

    @PostMapping("/add/new-room/{hotelId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<RoomDTO> addNewRoom(
            @RequestParam("photo")MultipartFile photo,
            @RequestParam("roomType")String roomType,
            @RequestParam("roomPrice")BigDecimal roomPrice,
            @RequestParam("roomDescription")String roomDescription,
            @PathVariable Long hotelId) throws SQLException, IOException {
        System.out.println("===========Is Running===========");
        Room savedRoom = roomServiceImpl.addNewRoom(photo, roomType, roomPrice, roomDescription, hotelId);
        RoomDTO response = new RoomDTO(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice(), savedRoom.getRoomDescription());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/room/types")
    public List<String> getRoomTypes() {
        return roomServiceImpl.getAllRoomTypes();
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<List<RoomDTO>> getRoomsByHotelId(@PathVariable Long hotelId) throws SQLException{
        List<Room> rooms = roomServiceImpl.getRoomByHotelId(hotelId);
        List<RoomDTO> roomResponses = new ArrayList<>();
        for(Room room : rooms){
            byte[] photoBytes = roomServiceImpl.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomDTO roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() throws SQLException{
        List<Room> rooms = roomServiceImpl.getAllRooms();
        List<RoomDTO> roomResponses = new ArrayList<>();
        for(Room room : rooms) {
            byte[] photoBytes = roomServiceImpl.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomDTO roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    @DeleteMapping("/delete/room/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomServiceImpl.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long roomId,
                                              @RequestParam("roomType") String roomType,
                                              @RequestParam("roomDescription") String roomDescription,
                                              @RequestParam("roomPrice") BigDecimal roomPrice,
                                              @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {
        Room theRoom = roomServiceImpl.updateRoom(roomId, roomType, roomDescription, roomPrice, photo);
        RoomDTO roomResponse = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomDTO>> getRoomById(@PathVariable Long roomId){
        Optional<Room> theRoom = roomServiceImpl.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomDTO roomResponse = getRoomResponse(room);
            return  ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    private List<BookedRoom> getAllBookingByRoomId(Long roomId) {
        return bookingServiceImpl.getAllBookingsByRoomId(roomId);
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
            @RequestParam("roomType") String roomType) throws SQLException {
        List<Room> availableRooms = roomServiceImpl.getAvailableRooms(checkInDate, checkOutDate, roomType);
        List<RoomDTO> roomResponses = new ArrayList<>();
        for (Room room : availableRooms){
            byte[] photoBytes = roomServiceImpl.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0){
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                RoomDTO roomResponse = getRoomResponse(room);
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

    private RoomDTO getRoomResponse(Room room) {
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalExcetion("Error retrieving photo");
            }
        }

        double averageNumberOfRoomStars = roomServiceImpl.averageNumberOfRoomStars(room.getId());

        return new RoomDTO(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.getRoomDescription(),
                room.isBooked(),
                photoBytes,
                averageNumberOfRoomStars);
    }
}


