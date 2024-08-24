package com.example.CoutingStarHotel.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RatingResponse {
    private Long id;
    private int star;
    private String comment;
    private LocalDate rateDay;
    private String userName;
    private String roomType;

    public RatingResponse(int star, String comment, LocalDate rateDay, String userName, String roomType) {
        this.star = star;
        this.comment = comment;
        this.rateDay = rateDay;
        this.userName = userName;
        this.roomType = roomType;
    }
}
