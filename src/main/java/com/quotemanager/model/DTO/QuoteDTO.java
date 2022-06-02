package com.quotemanager.model.DTO;

import java.time.format.DateTimeFormatter;

import com.quotemanager.model.Quote;

public class QuoteDTO {
	private Long id;
	private String date;
	private String price;
	private StockQuoteDTO stockQuoteDTO;
	private String stockQuoteId;

	public QuoteDTO() {
	}

	public QuoteDTO(String date, String price) {
		super();
		this.date = date;
		this.price = price;
	}
	public QuoteDTO(Long id, String date, String price) {
		super();
		this.id = id;
		this.date = date;
		this.price = price;
	}

	public QuoteDTO(Long id, String date, String price, String stockQuoteId) {
		super();
		this.id = id;
		this.date = date;
		this.price = price;
		this.stockQuoteId = stockQuoteId;
	}

	public QuoteDTO(Long id, String date, String price, StockQuoteDTO stockQuoteDTO) {
		super();
		this.id = id;
		this.date = date;
		this.price = price;
		this.stockQuoteDTO = stockQuoteDTO;
	}

	public Long getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}

	public String getPrice() {
		return price;
	}

	public StockQuoteDTO getStockQuoteDTO() {
		return stockQuoteDTO;
	}

	public String getStockQuoteId() {
		return stockQuoteId;
	}

	public static QuoteDTO ModeltoDTO(Quote q) {
		return new QuoteDTO(q.getId(), q.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), q.getPrice().toPlainString(),
				StockQuoteDTO.ModelToDTORL(q.getStockQuote()));
	}

	public static QuoteDTO ModeltoDTORL(Quote q) {
		return new QuoteDTO(q.getId(), q.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				q.getPrice().toPlainString());
	}
}
