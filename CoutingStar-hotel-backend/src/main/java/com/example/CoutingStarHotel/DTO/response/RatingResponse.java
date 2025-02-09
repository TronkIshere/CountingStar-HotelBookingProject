package com.example.CoutingStarHotel.DTO.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RatingResponse {
    private Long id;
    private int star;
    private String comment;
    private LocalDate rateDay;
    private String userName;
    private String roomType;
}
