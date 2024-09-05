package com.example.CoutingStarHotel.entities;

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
public class Discount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
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
