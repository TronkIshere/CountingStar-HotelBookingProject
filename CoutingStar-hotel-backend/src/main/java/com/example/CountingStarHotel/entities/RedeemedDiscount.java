package com.example.CountingStarHotel.entities;

import com.example.CountingStarHotel.entities.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedeemedDiscount extends AbstractEntity<Long> {
    boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Discount_id")
    Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id")
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookedRoom_id")
    BookedRoom bookedRoom;
}
