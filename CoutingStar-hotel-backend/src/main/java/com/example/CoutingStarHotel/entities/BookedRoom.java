package com.example.CoutingStarHotel.entities;

import com.example.CoutingStarHotel.entities.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookedRoom extends AbstractEntity<Long> {
    LocalDate checkInDate;
    LocalDate checkOutDate;
    String guestPhoneNumber;
    String guestFullName;
    String guestEmail;
    int NumOfAdults;
    int NumOfChildren;
    int totalNumOfGuest;
    String bookingConfirmationCode;
    LocalDate bookingDay;
    BigDecimal totalAmount;
    Boolean isCancelled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "bookedRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Rating> ratings;

    @OneToOne(mappedBy = "bookedRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    RedeemedDiscount redeemedDiscount;

    public BookedRoom(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void addComment(Rating rating) {
        rating.setBookedRoom(this);
    }

    public void addDiscount(RedeemedDiscount redeemedDiscount) {
        redeemedDiscount.setBookedRoom(this);
    }
}
