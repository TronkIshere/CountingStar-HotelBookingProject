package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PieChartDTO {
    private String cityName;
    private int quantity;

    public PieChartDTO(String cityName, long quantity) {
        this.cityName = cityName;
        this.quantity = (int) quantity;
    }
}
