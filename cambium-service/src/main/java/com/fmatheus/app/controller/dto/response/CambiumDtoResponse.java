package com.fmatheus.app.controller.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambiumDtoResponse {
    private Integer id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal conversionFactor;
    private BigDecimal convertedValue;
    private String environment;
}