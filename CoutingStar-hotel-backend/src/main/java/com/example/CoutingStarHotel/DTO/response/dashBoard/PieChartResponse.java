package com.example.CoutingStarHotel.DTO.response.dashBoard;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PieChartResponse implements Serializable {
    private String cityName;
    private int quantity;
    private String roomName;
    private BigDecimal roomRevenue;
}
