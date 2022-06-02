package com.quotemanager.model.DTO;

import java.util.Map;
import java.util.stream.Collectors;

import com.quotemanager.model.StockQuote;

public class StockQuoteDTO {
	private String id;
	private String stockId;
//	private List<QuoteDTO> quotesDTO;
	private Map<String, String> quotes;

	public StockQuoteDTO() {
	}

	public StockQuoteDTO(String id, String stockId) {
		super();
		this.id = id;
		this.stockId = stockId;
	}

	/*
	 * public StockQuoteDTO(String id, String stockId, List<QuoteDTO> quotesDTO) {
	 * super(); this.id = id; this.stockId = stockId; this.quotesDTO = quotesDTO; }
	 */

	public StockQuoteDTO(String id, String stockId, Map<String, String> quotes) {
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

	public Map<String, String> getQuotes() {
		return quotes;
	}

	public static StockQuoteDTO ModelToDTO(StockQuote s) {
		return new StockQuoteDTO(s.getId(), s.getStockId(),
				s.getQuotes().stream()
				.map(QuoteDTO::ModeltoDTORL)
				.collect(Collectors.toMap(QuoteDTO::getDate, QuoteDTO::getPrice)));
	}
	public static StockQuoteDTO ModelToDTORL(StockQuote s) {
		return new StockQuoteDTO(s.getId(), s.getStockId());
	}

}