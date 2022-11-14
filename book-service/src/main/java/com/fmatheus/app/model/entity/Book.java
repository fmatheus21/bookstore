package com.fmatheus.app.model.entity;

import com.fmatheus.app.controller.enumerable.CurrencyEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Id"})})
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "launch_date", nullable = false)
    private LocalDateTime launchDate;

    @Column(name = "price", nullable = false, scale = 8, precision = 2)
    private BigDecimal price;

    @Column(name = "title", nullable = false, length = 70)
    private String title;


    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 3)
    private CurrencyEnum currency;

}