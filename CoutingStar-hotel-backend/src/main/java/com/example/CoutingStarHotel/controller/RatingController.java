package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.entities.Rating;
import com.example.CoutingStarHotel.response.RatingResponse;
import com.example.CoutingStarHotel.services.impl.RatingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingServiceImpl ratingServiceImpl;
    @PostMapping("/add/hotel/{hotelId}/user/{userId}/addRating")
    public ResponseEntity<RatingResponse> addNewRating(@PathVariable Long hotelId,
                                                       @PathVariable Long userId,
                                                       @RequestParam("star") int star,
                                                       @RequestParam("comment") String comment,
                                                       @RequestParam("rateDay")String rateDay){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDate formatRateDay = LocalDate.parse(rateDay, dateTimeFormatter);
        Rating rating = ratingServiceImpl.saveRating(hotelId, userId , star, comment, formatRateDay);
        RatingResponse ratingResponse = new RatingResponse
                (rating.getStar(),
                        rating.getComment(),
                        rating.getRateDay(),
                        rating.getBookedRoom().getGuestFullName(),
                        rating.getBookedRoom().getRoom().getRoomType());

        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/hotel/{hotelId}")
        public ResponseEntity<List<RatingResponse>> getAllRatingByRoomId(@PathVariable Long hotelId){
            List<Rating> ratings = ratingServiceImpl.getAllRatingByHotelId(hotelId);
            List<RatingResponse> ratingRepositories = new ArrayList<>();
            for(Rating rating : ratings) {
                RatingResponse ratingResponse = getRatingResponse(rating);
                ratingRepositories.add(ratingResponse);
            }
        return ResponseEntity.ok(ratingRepositories);
    }

    @GetMapping("/hotel/{hotelId}/CheckUserRating/{userId}")
    public ResponseEntity<Boolean> checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(@PathVariable Long userId,
                                                                                                         @PathVariable Long hotelId){
        boolean result = ratingServiceImpl.checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(userId, hotelId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable Long ratingId,
                                                       @RequestParam("start") int star,
                                                       @RequestParam("comment") String comment){
        Rating rating = ratingServiceImpl.updateRating(ratingId, star, comment);
        RatingResponse ratingResponse = getRatingResponse(rating);
        return ResponseEntity.ok(ratingResponse);
    }

    @DeleteMapping("/delete/{ratingId}")
    public void deleteRating(@PathVariable Long ratingId) {
        ratingServiceImpl.deleteRating(ratingId);
    }

    private RatingResponse getRatingResponse(Rating rating){
        return new RatingResponse(
                rating.getStar(),
                rating.getComment(),
                rating.getRateDay(),
                rating.getBookedRoom().getGuestFullName(),
                rating.getBookedRoom().getRoom().getRoomType()
        );
    }
}

