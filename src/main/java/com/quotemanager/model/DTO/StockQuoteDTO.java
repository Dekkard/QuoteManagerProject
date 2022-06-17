package com.quotemanager.model.DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.quotemanager.model.StockQuote;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Data Transfer Object class used to transfer data between server and client of the stock type.")
/**
 * <b>StockQuoteDTO:</b> Data Transfer Object class used to transfer data
 * between server and client of the stock type.
 * <h6 style="margin-top:5px;margin-bottom:5px">Attributes</h6>
 * <ul>
 * <li><b>id:</b> UUID generated id for each stock.</li>
 * <li><b>stockId:</b>id that references the id from Stock Manager
 * application.</li>
 * <li><b>quotes:</b> a map attribute containing the date and price of quotes of
 * the Stock.</li>
 * </ul>
 */
public class StockQuoteDTO {
	@ApiModelProperty(notes = "UUID generated id for each stock.")
	private String id;
	@ApiModelProperty(notes = "ID that references the id from Stock Manager application.")
//	@Pattern(regexp = "([a-z]{4,5})(\\d{1,2})")
	private String stockId;
	@ApiModelProperty(notes = "A dictionary attribute containing the date and price of quotes of the Stock.")
//	@MapCheck
	private Map<String, String> quotes;

	public StockQuoteDTO() {
		super();
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

	public void sortQuotes() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.quotes = this.quotes.entrySet().stream()//
				.sorted(Map.Entry
						.comparingByKey((o1, o2) -> LocalDate.parse(o1, dtf).compareTo(LocalDate.parse(o2, dtf))))//
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> o, LinkedHashMap::new));
	}

	/**
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * <b>DTOToModel:</b> Convertion method from object to Data Transfer Object
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * Takes each attribute and tranforms into the appropriate attribute of the
	 * class.
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * Calls the relationless method from each related object to stop possible
	 * overflows.
	 * 
	 * @param Quote receives a object to be converted.
	 */
	public static StockQuoteDTO ModelToDTO(StockQuote stockQuote) {
		return new StockQuoteDTO(stockQuote.getId(), stockQuote.getStockId(), stockQuote.getQuotes().stream()
				.map(QuoteDTO::ModeltoDTORL).collect(Collectors.toMap(QuoteDTO::getDate, QuoteDTO::getPrice)));
	}

	/**
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * <b>DTOToModelRL:</b> Convertion method from object to Data Transfer Object
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * without the related objects intertwined by the DataBase relationships.
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * Useful to stop recursive method calling loop, otherwise resulting in a
	 * overflow.
	 * 
	 * @param StockQuote receives a object to be converted.
	 */
	public static StockQuoteDTO ModelToDTORL(StockQuote stockQuote) {
		return new StockQuoteDTO(stockQuote.getId(), stockQuote.getStockId());
	}

}