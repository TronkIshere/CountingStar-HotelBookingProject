package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PieChartDTO {
    private String cityName;
    private int quantity;

    private String roomName;
    private BigDecimal roomRevenue;

    public PieChartDTO(String cityName, long quantity) {
        this.cityName = cityName;
        this.quantity = (int) quantity;
    }

    public PieChartDTO(String roomName, BigDecimal roomRevenue) {
        this.roomName = roomName;
        this.roomRevenue = roomRevenue;
    }
}
