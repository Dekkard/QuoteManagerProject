package com.quotemanager.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
public class StockQuote {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	@Column(name = "stockId", nullable = false)
	private String stockId;
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
//	@Column(name = "quotes")
	private List<Quote> quotes;

	public StockQuote(String id, String stockId, List<Quote> quotes) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.quotes = quotes;
	}
}
