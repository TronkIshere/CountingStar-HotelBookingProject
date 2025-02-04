package com.example.CoutingStarHotel.entities;

import com.example.CoutingStarHotel.entities.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discount extends AbstractEntity {
    private String discountName;
    private int percentDiscount;
    private String discountDescription;
    private LocalDate createDate;
    private LocalDate expirationDate;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RedeemedDiscount> redeemedDiscount;

    public void addRedeemedDiscount(RedeemedDiscount redeemedDiscount) {
        redeemedDiscount.setDiscount(this);
    }


}
