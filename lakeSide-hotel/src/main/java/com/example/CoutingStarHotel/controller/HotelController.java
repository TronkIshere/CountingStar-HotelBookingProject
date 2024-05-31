package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exeption.InvalidBookingRequestException;
import com.example.CoutingStarHotel.model.Hotel;
import com.example.CoutingStarHotel.response.HotelResponse;
import com.example.CoutingStarHotel.service.IHotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final IHotelService hotelService;
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

    @PostMapping("/hotel/{userId}/saveHotel")
    public ResponseEntity<?> saveHotel(@PathVariable Long userId,
                                       @RequestBody Hotel hotelRequest){
        try{
            String ownerName = hotelService.saveHotel(userId, hotelRequest);
            return ResponseEntity.ok("Save hotel successfully with Owner name: " + ownerName);
        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/hotel/{hotelId}/delete")
    public void deleteHotel(@PathVariable Long hotelId){
        hotelService.deleteHotel(hotelId);
    }

    private HotelResponse getHotelResponse(Hotel hotel){
        return new HotelResponse(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getCity(),
                hotel.getHotelDescription(),
                hotel.getPhoneNumber(),
                hotel.getPhoto(),
                hotel.getRooms()
        );
    }
}
