package com.example.currencyconvertor.controller;


import com.example.currencyconvertor.model.Currency;
import com.example.currencyconvertor.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/convert/{from}/to/{to}")
    @ResponseBody
    public BigDecimal convert(@PathVariable String from, @PathVariable String to,
                              @RequestParam BigDecimal amount, @RequestParam LocalDate date) {
        Currency fromCurrency = currencyService.findByCode(from);
        Currency toCurrency = currencyService.findByCode(to);
        BigDecimal result = currencyService.convert(fromCurrency, toCurrency, amount, date);
        return result;
    }

    @GetMapping("/currencies")
    @ResponseStatus(HttpStatus.OK)
    public List<Currency> getCurrencies() {
        return currencyService.findAll();
    }

    @GetMapping("/rates/{code}")
    public ResponseEntity<BigDecimal> getRate(@PathVariable String code, @RequestParam LocalDate date) {
        Currency currency = currencyService.findByCode(code);
        BigDecimal rate = currency.getConversionFactor();
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }

}
