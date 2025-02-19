package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.rating.AddRatingRequest;
import com.example.CoutingStarHotel.DTO.request.rating.UpdateRatingRequest;
import com.example.CoutingStarHotel.DTO.response.rating.RatingResponse;
import com.example.CoutingStarHotel.DTO.response.common.ResponseData;
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

    @PostMapping("/hotel/{hotelId}/user/{userId}")
    public ResponseData<RatingResponse> addNewRating(@PathVariable Long hotelId,
                                                     @PathVariable Long userId,
                                                     @RequestBody AddRatingRequest request) {
        var result = ratingService.saveRating(hotelId, userId, request);
        return ResponseData.<RatingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseData<List<RatingResponse>> getAllRatingsByHotelId(@PathVariable Long hotelId) {
        var result = ratingService.getAllRatingByHotelId(hotelId);
        return ResponseData.<List<RatingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/hotel/{hotelId}/user/{userId}/check")
    public ResponseEntity<String> checkUserRating(@PathVariable Long userId,
                                                  @PathVariable Long hotelId) {
        String result = ratingService.checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(userId, hotelId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{ratingId}")
    public ResponseData<RatingResponse> updateRating(@PathVariable Long ratingId,
                                                     @RequestBody UpdateRatingRequest request) {
        var result = ratingService.updateRating(ratingId, request);
        return ResponseData.<RatingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/{ratingId}")
    public void deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
    }

    @PutMapping("/{ratingId}/soft-delete")
    public ResponseData<String> softDeleteRating(@PathVariable Long ratingId) {
        var result = ratingService.softDelete(ratingId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}


