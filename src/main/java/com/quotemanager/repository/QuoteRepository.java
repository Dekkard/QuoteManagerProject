package com.quotemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quotemanager.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>{
	List<Quote> findByStockId(String stockId);
}
