package com.example.CoutingStarHotel.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
@NoArgsConstructor
public class BarChartDTO {
    private String cityName;
    private BigDecimal revenue ;

    public BarChartDTO(String cityName, BigDecimal revenue) {
        this.cityName = cityName;
        this.revenue  = revenue ;
    }
}
