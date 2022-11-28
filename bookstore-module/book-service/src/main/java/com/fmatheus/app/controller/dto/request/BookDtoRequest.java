package com.fmatheus.app.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmatheus.app.controller.enumerable.CurrencyEnum;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDtoRequest {

    @NotNull
    @NotBlank
    private String author;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime launchDate;

    @NotNull
    private BigDecimal price;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private CurrencyEnum currency;
}
