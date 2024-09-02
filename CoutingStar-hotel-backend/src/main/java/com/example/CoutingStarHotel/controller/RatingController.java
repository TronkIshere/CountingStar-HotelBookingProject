package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.entities.Rating;
import com.example.CoutingStarHotel.DTO.RatingDTO;
import com.example.CoutingStarHotel.services.RatingService;
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
    private final RatingService ratingService;
    @PostMapping("/add/hotel/{hotelId}/user/{userId}/addRating")
    public ResponseEntity<RatingDTO> addNewRating(@PathVariable Long hotelId,
                                                  @PathVariable Long userId,
                                                  @RequestParam("star") int star,
                                                  @RequestParam("comment") String comment,
                                                  @RequestParam("rateDay")String rateDay){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDate formatRateDay = LocalDate.parse(rateDay, dateTimeFormatter);
        Rating rating = ratingService.saveRating(hotelId, userId , star, comment, formatRateDay);
        RatingDTO ratingResponse = new RatingDTO
                (rating.getStar(),
                        rating.getComment(),
                        rating.getRateDay(),
                        rating.getBookedRoom().getGuestFullName(),
                        rating.getBookedRoom().getRoom().getRoomType());

        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/hotel/{hotelId}")
        public ResponseEntity<List<RatingDTO>> getAllRatingByRoomId(@PathVariable Long hotelId){
            List<Rating> ratings = ratingService.getAllRatingByHotelId(hotelId);
            List<RatingDTO> ratingRepositories = new ArrayList<>();
            for(Rating rating : ratings) {
                RatingDTO ratingResponse = getRatingResponse(rating);
                ratingRepositories.add(ratingResponse);
            }
        return ResponseEntity.ok(ratingRepositories);
    }

    @GetMapping("/hotel/{hotelId}/CheckUserRating/{userId}")
    public ResponseEntity<Boolean> checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(@PathVariable Long userId,
                                                                                                         @PathVariable Long hotelId){
        boolean result = ratingService.checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(userId, hotelId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable Long ratingId,
                                                  @RequestParam("start") int star,
                                                  @RequestParam("comment") String comment){
        Rating rating = ratingService.updateRating(ratingId, star, comment);
        RatingDTO ratingResponse = getRatingResponse(rating);
        return ResponseEntity.ok(ratingResponse);
    }

    @DeleteMapping("/delete/{ratingId}")
    public void deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
    }

    private RatingDTO getRatingResponse(Rating rating){
        return new RatingDTO(
                rating.getStar(),
                rating.getComment(),
                rating.getRateDay(),
                rating.getBookedRoom().getGuestFullName(),
                rating.getBookedRoom().getRoom().getRoomType()
        );
    }
}

