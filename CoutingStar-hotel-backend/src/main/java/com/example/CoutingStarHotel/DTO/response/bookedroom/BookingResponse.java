package com.example.CoutingStarHotel.DTO.response.bookedroom;

import com.example.CoutingStarHotel.DTO.response.room.RoomResponse;
import com.example.CoutingStarHotel.entities.User;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookingResponse implements Serializable {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private String guestPhoneNumber;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;
    private BigDecimal totalAmount;
    private Boolean isCancelled;
    private LocalDate bookingDay;
    private RoomResponse room;
    private User user;
}


