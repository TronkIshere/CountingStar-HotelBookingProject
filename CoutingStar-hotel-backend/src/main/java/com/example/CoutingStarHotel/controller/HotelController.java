package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.InvalidHotelRequestException;
import com.example.CoutingStarHotel.exception.PhotoRetrievalExcetion;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.model.Hotel;
import com.example.CoutingStarHotel.model.Rating;
import com.example.CoutingStarHotel.model.Room;
import com.example.CoutingStarHotel.response.HotelResponse;
import com.example.CoutingStarHotel.service.IHotelService;
import com.example.CoutingStarHotel.service.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
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
    private final IHotelService hotelService;
    private final IRatingService ratingService;
    @PostMapping("/{userId}/addHotel")
    public ResponseEntity<?> addHotel(@PathVariable Long userId,
                                      @RequestParam String hotelName,
                                      @RequestParam String city,
                                      @RequestParam String hotelLocation,
                                      @RequestParam String hotelDescription,
                                      @RequestParam String phoneNumber,
                                      @RequestParam MultipartFile photo) throws SQLException, IOException {
        try {
            hotelService.addHotel(userId, hotelName, city, hotelLocation, hotelDescription, phoneNumber, photo);
            return ResponseEntity.ok("Đã đăng ký thành công");
        } catch (InvalidHotelRequestException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @GetMapping("/all-hotels")
    public ResponseEntity<List<HotelResponse>> getAllHotels(){
        List<Hotel> hotels = hotelService.getAllHotels();
        List<HotelResponse> hotelResponses = new ArrayList<>();
        for (Hotel hotel : hotels){
            HotelResponse hotelResponse = getHotelResponse(hotel);
            hotelResponses.add(hotelResponse);
        }
        return ResponseEntity.ok(hotelResponses);
    }

    @GetMapping("/homepage")
    public ResponseEntity<List<HotelResponse>> getFiveHotelForHomePage(){
        List<Hotel> hotels = hotelService.getFiveHotelForHomePage();
        List<HotelResponse> hotelResponses = new ArrayList<>();
        for (Hotel hotel : hotels){
            HotelResponse hotelResponse = getHotelResponse(hotel);
            hotelResponses.add(hotelResponse);
        }
        return ResponseEntity.ok(hotelResponses);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Optional<HotelResponse>> getHotelById(@PathVariable Long hotelId){
        Optional<Hotel> theHotel = hotelService.getHotelById(hotelId);
        return theHotel.map(room -> {
            HotelResponse hotelResponse = getHotelResponse(room);
            return  ResponseEntity.ok(Optional.of(hotelResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách sạn"));
    }

    @GetMapping("/{city}")
    public ResponseEntity<List<HotelResponse>> getHotelsByCity(@PathVariable String city){
        List<Hotel> hotels = hotelService.getAllHotelsByCity(city);
        List<HotelResponse> hotelResponses = hotels.stream()
                .map(this::getHotelResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hotelResponses);
    }

    @PutMapping("/hotel/{hotelId}/hotelInformationUpdate")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable Long hotelId,
                                                     @RequestParam String hotelName,
                                                     @RequestParam String city,
                                                     @RequestParam String hotelLocation,
                                                     @RequestParam String hotelDescription,
                                                     @RequestParam String phoneNumber,
                                                     @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {
        Hotel theHotel = hotelService.updateHotel(hotelId, hotelName, hotelLocation, hotelDescription, phoneNumber, city, photo);
        HotelResponse hotelResponse = getHotelResponse(theHotel);
        return ResponseEntity.ok(hotelResponse);
    }

    @DeleteMapping("/hotel/{hotelId}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public void deleteHotel(@PathVariable Long hotelId){
        hotelService.deleteHotel(hotelId);
    }

    private HotelResponse getHotelResponse(Hotel hotel){
        byte[] photoBytes = null;
        Blob photoBlob = hotel.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalExcetion("Error retrieving photo");
            }
        }

        double averageNumberOfHotelStars = hotelService.averageNumberOfHotelStars(hotel.getId());
        Long lowestPrice
                = hotelService.getHotelLowestPriceByHotelId(hotel.getId())
                != null ? hotelService.getHotelLowestPriceByHotelId(hotel.getId()) : 0;
        Long highestPrice
                = hotelService.getHotelHighestPriceByHotelId(hotel.getId())
                != null ? hotelService.getHotelHighestPriceByHotelId(hotel.getId()) : 0;

        return new HotelResponse(
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
