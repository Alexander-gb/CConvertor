package com.example.currencyconvertor.service;

import com.example.currencyconvertor.model.Currency;
import com.example.currencyconvertor.repository.CurrencyReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyReporsitory currencyRepository;

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> findById(String id) {
        return currencyRepository.findById(id);
    }

    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    public BigDecimal convert(Currency fromCurrency, Currency toCurrency, BigDecimal amount, LocalDate date) {
        BigDecimal fromConversionFactor = fromCurrency.getConversionFactor();
        BigDecimal toConversionFactor = toCurrency.getConversionFactor();
        BigDecimal rate = toConversionFactor.divide(fromConversionFactor, 10, BigDecimal.ROUND_HALF_UP);
        return amount.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}