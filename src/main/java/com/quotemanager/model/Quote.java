package com.quotemanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.quotemanager.model.DTO.QuoteDTO;

@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name = "stock_quote_id", referencedColumnName = "id")
	private StockQuote stockQuote;

	public Quote() {
	}

	public Quote(LocalDate date, BigDecimal price) {
		super();
		this.date = date;
		this.price = price;
	}

	public Quote(Long id, LocalDate date, BigDecimal price) {
		super();
		this.id = id;
		this.date = date;
		this.price = price;
	}

	public Quote(LocalDate date, BigDecimal price, StockQuote stockQuote) {
		super();
		this.date = date;
		this.price = price;
		this.stockQuote = stockQuote;
	}
	
	public Quote(Long id, LocalDate date, BigDecimal price, StockQuote stockQuote) {
		super();
		this.id = id;
		this.date = date;
		this.price = price;
		this.stockQuote = stockQuote;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public StockQuote getStockQuote() {
		return stockQuote;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setStockQuote(StockQuote stockQuote) {
		this.stockQuote = stockQuote;
	}

	public static Quote DTOtoModel(QuoteDTO q) {
		return new Quote(q.getId(), LocalDate.parse(q.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				new BigDecimal(q.getPrice()), StockQuote.DTOToModelRL(q.getStockQuoteDTO()));
	}

	public static Quote DTOtoModelRL(QuoteDTO q) {
		return new Quote(q.getId(), LocalDate.parse(q.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				new BigDecimal(q.getPrice()));
	}
}
