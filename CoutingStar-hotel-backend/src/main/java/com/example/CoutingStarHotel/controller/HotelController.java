package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.BarChartDTO;
import com.example.CoutingStarHotel.DTO.PieChartDTO;
import com.example.CoutingStarHotel.exception.InvalidHotelRequestException;
import com.example.CoutingStarHotel.exception.PhotoRetrievalExcetion;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.entities.Hotel;
import com.example.CoutingStarHotel.DTO.HotelDTO;
import com.example.CoutingStarHotel.services.impl.HotelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final HotelServiceImpl hotelServiceImpl;
    @PostMapping("/{userId}/addHotel")
    public ResponseEntity<?> addHotel(@PathVariable Long userId,
                                      @RequestParam String hotelName,
                                      @RequestParam String city,
                                      @RequestParam String hotelLocation,
                                      @RequestParam String hotelDescription,
                                      @RequestParam String phoneNumber,
                                      @RequestParam MultipartFile photo) throws SQLException, IOException {
        try {
            hotelServiceImpl.addHotel(userId, hotelName, city, hotelLocation, hotelDescription, phoneNumber, photo);
            return ResponseEntity.ok("Đã đăng ký thành công");
        } catch (InvalidHotelRequestException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @GetMapping("/all-hotels")
    public ResponseEntity<List<HotelDTO>> getAllHotels(){
        List<Hotel> hotels = hotelServiceImpl.getAllHotels();
        List<HotelDTO> hotelResponses = new ArrayList<>();
        for (Hotel hotel : hotels){
            HotelDTO hotelResponse = getHotelResponse(hotel);
            hotelResponses.add(hotelResponse);
        }
        return ResponseEntity.ok(hotelResponses);
    }

    @GetMapping("/homepage")
    public ResponseEntity<List<HotelDTO>> getFiveHotelForHomePage(){
        List<Hotel> hotels = hotelServiceImpl.getFiveHotelForHomePage();
        List<HotelDTO> hotelResponses = new ArrayList<>();
        for (Hotel hotel : hotels){
            HotelDTO hotelResponse = getHotelResponse(hotel);
            hotelResponses.add(hotelResponse);
        }
        return ResponseEntity.ok(hotelResponses);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Optional<HotelDTO>> getHotelById(@PathVariable Long hotelId){
        Optional<Hotel> theHotel = hotelServiceImpl.getHotelById(hotelId);
        return theHotel.map(room -> {
            HotelDTO hotelResponse = getHotelResponse(room);
            return  ResponseEntity.ok(Optional.of(hotelResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách sạn"));
    }

    @GetMapping("/{city}")
    public ResponseEntity<List<HotelDTO>> getHotelsByCity(@PathVariable String city){
        List<Hotel> hotels = hotelServiceImpl.getAllHotelsByCity(city);
        List<HotelDTO> hotelResponses = hotels.stream()
                .map(this::getHotelResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hotelResponses);
    }

    @PutMapping("/hotel/{hotelId}/hotelInformationUpdate")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long hotelId,
                                                @RequestParam String hotelName,
                                                @RequestParam String city,
                                                @RequestParam String hotelLocation,
                                                @RequestParam String hotelDescription,
                                                @RequestParam String phoneNumber,
                                                @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {
        Hotel theHotel = hotelServiceImpl.updateHotel(hotelId, hotelName, hotelLocation, hotelDescription, phoneNumber, city, photo);
        HotelDTO hotelResponse = getHotelResponse(theHotel);
        return ResponseEntity.ok(hotelResponse);
    }


    @DeleteMapping("/hotel/{hotelId}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public void deleteHotel(@PathVariable Long hotelId){
        hotelServiceImpl.deleteHotel(hotelId);
    }

    private HotelDTO getHotelResponse(Hotel hotel){
        byte[] photoBytes = null;
        Blob photoBlob = hotel.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalExcetion("Error retrieving photo");
            }
        }

        double averageNumberOfHotelStars = hotelServiceImpl.averageNumberOfHotelStars(hotel.getId());
        Long lowestPrice
                = hotelServiceImpl.getHotelLowestPriceByHotelId(hotel.getId())
                != null ? hotelServiceImpl.getHotelLowestPriceByHotelId(hotel.getId()) : 0;
        Long highestPrice
                = hotelServiceImpl.getHotelHighestPriceByHotelId(hotel.getId())
                != null ? hotelServiceImpl.getHotelHighestPriceByHotelId(hotel.getId()) : 0;

        return new HotelDTO(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getCity(),
                hotel.getHotelLocation(),
                hotel.getHotelDescription(),
                hotel.getPhoneNumber(),
                photoBytes,
                averageNumberOfHotelStars,
                lowestPrice,
                highestPrice
        );
    }

}
