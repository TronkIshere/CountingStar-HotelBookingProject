package com.example.CountingStarHotel.entities;

import com.example.CountingStarHotel.entities.common.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Discount extends AbstractEntity<Long> {
    String discountName;
    int percentDiscount;
    String discountDescription;
    LocalDate createDate;
    LocalDate expirationDate;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<RedeemedDiscount> redeemedDiscount;

    public void addRedeemedDiscount(RedeemedDiscount redeemedDiscount) {
        redeemedDiscount.setDiscount(this);
    }


}
