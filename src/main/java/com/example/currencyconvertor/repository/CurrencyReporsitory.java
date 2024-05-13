package com.example.currencyconvertor.repository;


import com.example.currencyconvertor.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyReporsitory extends JpaRepository<Currency, String> {

    Optional<Currency> findById(String id);

    Currency findByCode(String code);

}