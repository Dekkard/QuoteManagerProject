package com.quotemanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quotemanager.model.StockQuote;

public interface StockQuoteRepository extends JpaRepository<StockQuote, String>{
	Optional<StockQuote> findByStockId(String stockId);
}
