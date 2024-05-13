package com.example.currencyconvertor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity


public class Currency {
    @Id
    String name;
    String code;
    BigDecimal quantity;
    BigDecimal conversionFactor;
    LocalDate date;

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }
}
