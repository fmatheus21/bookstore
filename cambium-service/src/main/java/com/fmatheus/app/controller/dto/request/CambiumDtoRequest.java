package com.fmatheus.app.controller.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambiumDtoRequest {

    @NotNull
    @NotBlank
    private String fromCurrency;

    @NotNull
    @NotBlank
    private String toCurrency;

    @NotNull
    private BigDecimal conversionFactor;

}