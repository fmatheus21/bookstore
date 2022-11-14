package com.fmatheus.app.controller.dto.response;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDtoResponse {
    private Integer id;
    private String author;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime launchDate;
    private BigDecimal price;
    private String title;
    private String currency;
    private String environment;
}
