package com.quotemanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.quotemanager.model.DTO.StockQuoteDTO;

@Entity
public class StockQuote {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	private String stockId;
	@OneToMany(mappedBy = "stockQuote")
	private List<Quote> quotes;

	public StockQuote() {
	}

	public StockQuote(String id, String stockId) {
		super();
		this.id = id;
		this.stockId = stockId;
	}

	public StockQuote(String id, String stockId, List<Quote> quotes) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.quotes = quotes;
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public static StockQuote DTOToModel(StockQuoteDTO s) {
		List<Quote> listQuote = s.getQuotes().entrySet().stream()
				.map(e -> new Quote(LocalDate.parse(e.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						new BigDecimal(e.getValue()), StockQuote.DTOToModelRL(s)))
				.collect(Collectors.toList());
		return new StockQuote(s.getId(), s.getStockId(), listQuote);
	}

	public static StockQuote DTOToModelRL(StockQuoteDTO s) {
		return new StockQuote(s.getId(), s.getStockId());
	}
}
