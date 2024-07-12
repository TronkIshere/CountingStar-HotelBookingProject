package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.BookedRoom;
import com.example.CoutingStarHotel.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class RatingResponse {
    private Long id;
    private int star;
    private String comment;
    private LocalDate rateDay;
    private User user;
    private BookedRoom bookedRoom;

    public RatingResponse(int star, String comment, LocalDate rateDay) {
        this.star = star;
        this.comment = comment;
        this.rateDay = rateDay;
    }
}
