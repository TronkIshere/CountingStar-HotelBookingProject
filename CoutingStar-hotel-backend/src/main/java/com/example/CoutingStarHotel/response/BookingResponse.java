package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;
    private RoomResponse room;
    private User user;

    public BookingResponse(Long bookingId,
                           LocalDate checkInDate,
                           LocalDate checkOutDate,
                           String bookingConfirmationCode,
                           String guestEmail,
                           int numOfAdults,
                           int numOfChildren,
                           int totalNumOfGuest,
                           String guestFullName, RoomResponse room) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.guestEmail = guestEmail;
        this.numOfAdults = numOfAdults;
        this.numOfChildren = numOfChildren;
        this.totalNumOfGuest = totalNumOfGuest;
        this.guestFullName = guestFullName;
        this.room = room;
    }
}


