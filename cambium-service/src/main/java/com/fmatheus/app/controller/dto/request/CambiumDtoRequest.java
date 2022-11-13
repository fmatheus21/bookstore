package com.fmatheus.app.controller.dto.request;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambiumDtoRequest {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal conversionFactor;
}