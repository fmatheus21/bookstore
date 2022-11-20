package com.fmatheus.app.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmatheus.app.exception.handler.response.MessageResponse;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CambiumDtoResponse {
    private Integer id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal conversionFactor;
    private BigDecimal convertedValue;
    private MessageResponse message;
}