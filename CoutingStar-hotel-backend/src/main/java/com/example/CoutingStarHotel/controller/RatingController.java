package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.AddRatingRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateRatingRequest;
import com.example.CoutingStarHotel.DTO.response.RatingResponse;
import com.example.CoutingStarHotel.DTO.response.ResponseData;
import com.example.CoutingStarHotel.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;
    @PostMapping("/add/hotel/{hotelId}/user/{userId}/addRating")
    public ResponseData<RatingResponse> addNewRating(@PathVariable Long hotelId,
                                                       @PathVariable Long userId,
                                                       @RequestBody AddRatingRequest request){
        var result = ratingService.saveRating(hotelId, userId , request);
        return ResponseData.<RatingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotel/{hotelId}")
        public ResponseData<List<RatingResponse>> getAllRatingByRoomId(@PathVariable Long hotelId){
            var result = ratingService.getAllRatingByHotelId(hotelId);
        return ResponseData.<List<RatingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotel/{hotelId}/CheckUserRating/{userId}")
    public ResponseEntity<String> checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(@PathVariable Long userId,
                                                                                                         @PathVariable Long hotelId){
        String result = ratingService.checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(userId, hotelId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseData<RatingResponse> updateRating(@PathVariable Long ratingId,
                                                     @RequestBody UpdateRatingRequest request){
        var result = ratingService.updateRating(ratingId, request);
        return ResponseData.<RatingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/delete/{ratingId}")
    public void deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
    }
}

