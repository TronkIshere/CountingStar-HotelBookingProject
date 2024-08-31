package com.example.CoutingStarHotel.DTO;

import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Discount;
import com.example.CoutingStarHotel.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedeemedDiscountDTO {
    private Long id;
    private boolean isUsed;

    private Discount discount;
    private User user;
    private BookedRoom bookedRoom;

    private String discountName;
    private int percentDiscount;
    private String discountDescription;

    public RedeemedDiscountDTO(Long id, boolean isUsed, String discountName, int percentDiscount, String discountDescription) {
        this.id = id;
        this.isUsed = isUsed;
        this.discountName = discountName;
        this.percentDiscount = percentDiscount;
        this.discountDescription = discountDescription;
    }
}
