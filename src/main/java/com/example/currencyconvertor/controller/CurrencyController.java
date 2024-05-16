package com.example.currencyconvertor.controller;

import com.example.currencyconvertor.model.Currency;
import com.example.currencyconvertor.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/convert/{from}/to/{to}")
    @ResponseBody
    public ResponseEntity<String> convert(@PathVariable String from, @PathVariable String to,
                                          @RequestParam BigDecimal amount, @RequestParam LocalDate date) {
        try {
            Currency fromCurrency = currencyService.findByCode(from);
            Currency toCurrency = currencyService.findByCode(to);
            BigDecimal result = currencyService.convert(fromCurrency, toCurrency, amount, date);

            MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.getDefault());
            MonetaryAmount resultAmount = Monetary.getDefaultAmountFactory().setCurrency(toCurrency.getCurrencyUnit()).setNumber(result).create();
            String formattedResult = format.format(resultAmount);

            return new ResponseEntity<>(formattedResult, HttpStatus.OK);
        } catch (UnknownCurrencyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
