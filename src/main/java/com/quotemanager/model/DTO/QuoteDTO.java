package com.quotemanager.model.DTO;

import java.time.format.DateTimeFormatter;

import com.quotemanager.model.Quote;

/**
 * <b>Stock:<b> Model class that describes the Stock, imported from another
 * application
 * <h6 style="margin-top:5px;margin-bottom:5px">Attributes</h6>
 * <ul>
 * <li><b>id:</b> Sequencial generated id for each quote.</li>
 * <li><b>date:</b> Date in which the quote was issued.</li>
 * <li><b>price:</b> Price of the quote at the given date.</li>
 * <li><b>stockQuoteDTO:</b> Relational entity in which the quote is related to.
 * <li><b>stockQuoteId:</b> Relational entity id used to retrieve data from the
 * Data Base.</li>
 * </ul>
 */
public class QuoteDTO {
	private Long id;
	private String date;
	private String price;
	private StockQuoteDTO stockQuoteDTO;
	private String stockQuoteId;

	public QuoteDTO() {
		super();
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
	public static QuoteDTO ModeltoDTO(Quote q) {
		return new QuoteDTO(q.getId(), q.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				q.getPrice().toPlainString(), StockQuoteDTO.ModelToDTORL(q.getStockQuote()));
	}

	/**
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * <b>DTOToModelRL:</b> Convertion method from object to Data Transfer Object
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * without the related objects intertwined by the DataBase relationships.
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * Useful to stop recursive method calling loop, otherwise resulting in a overflow.
	 * 
	 * @param Quote receives a object to be converted.
	 */
	public static QuoteDTO ModeltoDTORL(Quote q) {
		return new QuoteDTO(q.getId(), q.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				q.getPrice().toPlainString());
	}
}
