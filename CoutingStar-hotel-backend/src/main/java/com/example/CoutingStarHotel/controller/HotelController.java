package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exception.PhotoRetrievalExcetion;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.model.Hotel;
import com.example.CoutingStarHotel.model.Rating;
import com.example.CoutingStarHotel.model.Room;
import com.example.CoutingStarHotel.response.HotelResponse;
import com.example.CoutingStarHotel.service.IHotelService;
import com.example.CoutingStarHotel.service.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
                                      @RequestParam("hotelName") String hotelName,
                                      @RequestParam("city") String city,
                                      @RequestParam("hotelDescription") String hotelDescription,
                                      @RequestParam("phoneNumber") String phoneNumber,
                                      @RequestParam("photo") MultipartFile photo) throws SQLException, IOException {
        String hotelOwnerName = hotelService.addHotel(userId, hotelName, city, hotelDescription, phoneNumber, photo);
        return ResponseEntity.ok(hotelOwnerName);
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
        }).orElseThrow(() -> new ResourceNotFoundException("hotel not found"));
    }

    @GetMapping("/hotel/{hotelId}/hotelLowestPrice")
    public ResponseEntity<Long> getHotelLowestPriceByHotelId(@PathVariable Long hotelId){
        Long hotelLowestPrice = hotelService.getHotelLowestPriceByHotelId(hotelId);
        return ResponseEntity.ok(hotelLowestPrice);
    }

    @GetMapping("/hotel/{hotelId}/hotelHighestPrice")
    public ResponseEntity<Long> getHotelHighestPriceByHotelId(@PathVariable Long hotelId){
        Long hotelHighestPrice = hotelService.getHotelHighestPriceByHotelId(hotelId);
        return ResponseEntity.ok(hotelHighestPrice);
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

        return new HotelResponse(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getCity(),
                hotel.getHotelLocation(),
                hotel.getHotelDescription(),
                hotel.getPhoneNumber(),
                photoBytes,
                AverageNumberOfHotelStars(hotel.getId())
        );
    }

    private double AverageNumberOfHotelStars(Long hotelId){
        double result = 0;
        int count = 0;
        List<Rating> ratings = ratingService.getAllRatingByHotelId(hotelId);
        for(Rating rating: ratings){
            result += rating.getStar();
            count++;
        }
        return result/count;
    }
}
