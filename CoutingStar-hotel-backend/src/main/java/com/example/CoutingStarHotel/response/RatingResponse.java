package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.BookedRoom;
import com.example.CoutingStarHotel.model.User;
import lombok.Data;
import lombok.Getter;
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
