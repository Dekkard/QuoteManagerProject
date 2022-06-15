package com.quotemanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.quotemanager.model.DTO.QuoteDTO;
import com.quotemanager.model.validation.Price;

/**
 * <b>Quote:</b> entity class to store quotes from each stock.
 * <h6 style="margin-top:5px;margin-bottom:5px">Attributes</h6>
 * <ul>
 * <li><b>id:</b> Sequencial generated id for each quote.</li>
 * <li><b>date:</b> Date in which the quote was issued.</li>
 * <li><b>price:</b> Price of the quote at the given date.</li>
 * <li><b>stockQuote:</b> Relational entity in which the quote is related
 * to.</li>
 * </ul>
 */
@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	@Price(message = "")
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name = "stock_quote_id", referencedColumnName = "id")
	private StockQuote stockQuote;

	public Quote() {
		super();
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

	@Override
	public int hashCode() {
		return Objects.hash(id, date, price);
	}

	@Override
	public boolean equals(Object obj) {
		Quote quote = (Quote) obj;
		return this.id.equals(quote.getId())
				&& this.date.equals(quote.getDate())
				&& this.price.equals(quote.getPrice());
	}

	/**
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * <b>DTOToModel:</b> Convertion method from Data Transfer Object to actual
	 * Object.
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * Takes each attribute and tranforms into the appropriate attribute of the
	 * class.
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * Calls the relationless method from each related object to stop possible
	 * overflows.
	 * 
	 * @param quoteDTO receives a data transfer object to be converted.
	 */
	public static Quote DTOtoModel(QuoteDTO quoteDTO) {
		return new Quote(quoteDTO.getId(), //
				LocalDate.parse(quoteDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), //
				new BigDecimal(quoteDTO.getPrice()), //
				StockQuote.DTOToModelRL(quoteDTO.getStockQuoteDTO()));
	}

	/**
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * <b>DTOToModelRL:</b> Convertion method from Data Transfer Object to actual
	 * Object,
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * without the related objects intertwined by the DataBase relationships.
	 * <p style="margin-top:0px;margin-bottom:0px">
	 * Useful to stop recursive method calling loop, otherwise resulting in a
	 * overflow.
	 * 
	 * @param quoteDTO receives a data transfer object to be converted.
	 */
	public static Quote DTOtoModelRL(QuoteDTO quoteDTO) {
		return new Quote(quoteDTO.getId(),
				LocalDate.parse(quoteDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				new BigDecimal(quoteDTO.getPrice()));
	}
}
