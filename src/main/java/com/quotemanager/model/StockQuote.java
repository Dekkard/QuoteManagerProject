package com.quotemanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import com.quotemanager.model.DTO.StockQuoteDTO;

/**
 * <b>StockQuote:</b> entity class to store stocks objects.
 * <h6 style="margin-top:5px;margin-bottom:5px">Attributes</h6>
 * <ul>
 * <li><b>id:</b> UUID generated id for each stock.</li>
 * <li><b>stockId:</b> Stock id that references to the</li>
 * </ul>
 */
@Entity
public class StockQuote {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	private String stockId;
	@OneToMany(mappedBy = "stockQuote")
	private List<Quote> listQuote;

	public StockQuote() {
		super();
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
		this.listQuote = quotes;
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public List<Quote> getQuotes() {
		return listQuote;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public void setQuotes(List<Quote> quotes) {
		this.listQuote = quotes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, stockId);
	}

	@Override
	public boolean equals(Object obj) {
		StockQuote stockQuote = (StockQuote) obj;
		return id.equals(stockQuote.getId()) //
				&& stockId.equals(stockQuote
						.getStockId())/*
										 * // && listQuote.parallelStream()// .anyMatch(sq -> stockQuote.getQuotes()//
										 * .parallelStream()// .anyMatch(s -> sq.equals(s)))
										 */;
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
	 * @param stockQuoteDTO receives a data transfer object to be converted.
	 */
	public static StockQuote DTOToModel(@Validated StockQuoteDTO stockQuoteDTO) {
		List<Quote> listQuote = stockQuoteDTO.getQuotes().entrySet().stream()
				.map(e -> new Quote(LocalDate.parse(e.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						new BigDecimal(e.getValue()), StockQuote.DTOToModelRL(stockQuoteDTO)))
				.collect(Collectors.toList());
		return new StockQuote(stockQuoteDTO.getId(), stockQuoteDTO.getStockId(), listQuote);
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
	 * @param StockQuoteDTO receives a data transfer object to be converted.
	 */
	public static StockQuote DTOToModelRL(StockQuoteDTO s) {
		return new StockQuote(s.getId(), s.getStockId());
	}
}
