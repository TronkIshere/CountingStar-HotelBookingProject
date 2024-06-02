package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.BookedRoom;
import com.example.CoutingStarHotel.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RatingResponse {
    private Long id;
    private int star;
    private String comment;
    private Date rateDay;
    private User user;
    private BookedRoom bookedRoom;

    public RatingResponse(int star, String comment, Date rateDay) {
        this.star = star;
        this.comment = comment;
        this.rateDay = rateDay;
    }
}
