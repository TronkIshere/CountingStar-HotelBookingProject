package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.model.Rating;
import com.example.CoutingStarHotel.response.RatingResponse;
import com.example.CoutingStarHotel.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;
    @PostMapping("/add/{bookedRoomId}/addRating")
    public ResponseEntity<RatingResponse> addNewRating(@PathVariable Long bookedRoomId,
                                                       @RequestParam("start") int star,
                                                       @RequestParam("comment") String comment,
                                                       @RequestParam("rateDay")Date rateDay,
                                                       @RequestParam(required = false) Long userId){
        Rating rating = ratingService.saveRating(bookedRoomId, star, comment, rateDay, userId);
        RatingResponse ratingResponse = new RatingResponse(rating.getStar(), rating.getComment(),rating.getRateDay());

        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/room/{roomId}/Rating")
        public ResponseEntity<List<RatingResponse>> getAllRatingByRoomId(@PathVariable Long roomId){
            List<Rating> ratings = ratingService.getAllRatingByRoomId(roomId);
            List<RatingResponse> ratingRepositories = new ArrayList<>();
            for(Rating rating : ratings) {
                RatingResponse ratingResponse = getRatingResponse(rating);
                ratingRepositories.add(ratingResponse);
            }
        return ResponseEntity.ok(ratingRepositories);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable Long ratingId,
                                                       @RequestParam("start") int star,
                                                       @RequestParam("comment") String comment){
        Rating rating = ratingService.updateRating(ratingId, star, comment);
        RatingResponse ratingResponse = getRatingResponse(rating);
        return ResponseEntity.ok(ratingResponse);
    }

    @DeleteMapping("/delete/{ratingId}")
    public void deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
    }

    private RatingResponse getRatingResponse(Rating rating){
        return new RatingResponse(
                rating.getStar(),
                rating.getComment(),
                rating.getRateDay()
        );
    }
}

/*
    * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⠀⢠⠀⠀⠀⠀⠀⠀⣠⠠⠀⠀⠂⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⡀⠀⠤⠀⠀⠀⠄⠀⠀⠀⠀⠀⡠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢈⡀⠐⠀⣆⠀⠀⠀⠀⠀⠐⠠⠀⠀⠑⢈⠓⠄⠠⢀⡀⢀⠀⠀⠀⠀⠀⢀⡀⡀⣀⠢⣄⡔⣁⣀⠢⠀⠑⠀⠀⢀⣞⠈⠀⣴⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢷⢐⡈⢻⠢⠀⠀⠀⠛⢉⠘⠒⠂⠠⠄⢀⠀⢀⠀⠀⠀⠈⠉⠉⠁⠀⠀⠀⠀⠀⢀⢔⣀⣀⠀⠀⠀⠀⠀⢀⠺⠈⢴⣕⠞⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⢸⢆⠶⡆⢀⠀⡀⡀⢊⣉⠙⠒⠮⢀⣄⡀⠀⠈⠁⠂⠠⠤⠀⠀⠀⢠⠄⢒⠡⠏⠁⠀⡉⡉⡉⠒⠄⢀⠌⠚⢸⣇⡌⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠪⠿⡷⠘⠀⠡⠜⣰⣩⣦⣭⣠⣀⡀⠙⠙⠓⠠⠀⡄⠀⢀⣦⠀⡀⠈⢀⠀⣁⣀⣴⣦⣴⣬⣩⣄⡒⠁⣠⠙⢿⠁⠒⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠢⠁⡀⣅⡀⣺⣿⣿⣿⣿⣿⣿⣽⣶⣶⣶⣶⣿⣶⡯⢍⡻⠿⣿⣿⣿⣿⣿⣿⣽⣿⣯⡛⡿⢿⡄⢠⡎⢀⡔⠪⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠇⠿⢧⠉⠹⣿⡿⣻⣯⣟⣛⡭⣿⣿⣿⣿⣿⡷⠦⠴⣿⡿⡿⢿⣧⡝⣛⣯⣿⠿⢳⣗⠁⠘⡟⠃⠺⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢲⣾⠀⢸⠀⠀⠉⠳⢦⡼⠍⠽⢋⣏⠳⣼⣿⣿⠀⠀⠀⢸⣿⡷⢿⣿⣙⠫⠅⢁⣰⠯⠀⠀⢀⠇⠀⠿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⠀⠘⣆⠀⠀⠀⠀⠉⠤⠔⠋⠈⢀⣾⣿⠇⠀⠀⠀⠀⢻⣷⡈⠑⠓⠱⠲⠚⠃⠀⢀⣠⡞⠀⠀⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⡹⣳⢤⣤⣀⣀⣀⣄⣤⠼⢿⡛⠉⠉⠀⠀⠀⠀⠈⠙⠻⠶⢤⠤⣦⣴⣴⠶⠟⠑⡜⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⢱⣿⢿⡉⠋⠈⠀⠀⢀⣠⣿⠛⠀⠀⠀⠀⠀⠀⠀⠀⢷⣦⠀⠀⠀⠀⢀⠀⣌⢼⠼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣸⣹⡇⠀⠀⠀⠀⣾⣿⣇⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⣿⣷⣀⠀⠀⠀⢀⡏⣀⡎⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠠⠁⢳⢗⣿⠈⣠⠰⣸⠟⠈⢿⣿⣦⣄⣀⠀⣄⣁⣠⣶⣾⣄⢙⣷⠂⢀⡀⣸⢬⠀⠀⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣦⠆⠀⠍⣿⣮⠜⠁⣴⣼⣾⣿⣿⣿⣿⣿⣿⣷⢿⣿⣿⣿⣿⣷⣿⣯⢳⢒⡿⣃⢀⢠⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣶⣿⣿⣿⣾⡐⠆⢿⡇⡰⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⢘⠟⢆⣬⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⣾⣿⣿⣿⣿⣿⣿⡇⢇⢿⡧⣴⣿⣿⣿⠿⣿⢿⣿⣿⣿⣯⣿⣿⣿⣿⣿⣟⠛⣿⣿⣿⣸⢥⣼⣿⣿⣿⣿⣿⣿⣿⣿⣶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇⠈⢿⣗⢻⣽⣿⡏⠀⠀⠀⢀⣀⣤⣤⣤⣤⣀⡄⡀⠆⡄⢰⣾⣯⣭⢮⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠀⠀⠈⣿⡜⣽⣿⣧⡎⠔⣰⣽⣿⣿⣿⣿⣿⣿⣿⣦⣿⣶⣿⣿⣿⣵⠃⣟⡻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⣠⣴⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠋⠀⠠⠘⢀⢺⡷⣿⣿⣿⣿⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣷⡪⣫⣿⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣄⡀⠀⠀⠀⠀
⠀⠀⠀⢀⣠⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⠐⠀⡑⢊⠌⣻⣧⣽⣿⣿⣿⣿⣿⣿⣿⣿⣯⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⣵⣿⡯⣡⣿⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⡀⠀
⣠⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣔⠥⣉⠻⢹⡿⣿⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⣥⣚⡹⢟⣿⣧⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⣷⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿*/
