package com.example.CoutingStarHotel.DTO;

import com.example.CoutingStarHotel.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingDTO {
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private String guestPhoneNumber;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;
    private RoomDTO room;
    private User user;
    private Boolean isCancelled;

    public BookingDTO(Long bookingId,
                      LocalDate checkInDate,
                      LocalDate checkOutDate,
                      String bookingConfirmationCode,
                      String guestEmail,
                      String guestPhoneNumber,
                      int numOfAdults,
                      int numOfChildren,
                      int totalNumOfGuest,
                      String guestFullName, RoomDTO room,
                      Boolean isCancelled) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.guestEmail = guestEmail;
        this.guestPhoneNumber = guestPhoneNumber;
        this.numOfAdults = numOfAdults;
        this.numOfChildren = numOfChildren;
        this.totalNumOfGuest = totalNumOfGuest;
        this.guestFullName = guestFullName;
        this.room = room;
        this.isCancelled = isCancelled;
    }
}


