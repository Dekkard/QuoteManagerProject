package com.quotemanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
public class Quote {
	@Id
	@GeneratedValue()
	@Column(name = "id", unique = true, nullable = false)
	private long id;
	@Column(name = "stockId", nullable = false)
	private String stockId;
	@Column(name = "data", nullable = false)
	private Date data;
	@Column(name = "price", nullable = false)
	private Double price;

	public Quote(Long id, String stockId, Date data, Double price) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.data = data;
		this.price = price;
	}

	public Quote(String stockId, Date data, Double price) {
		super();
//		this.id = id;
		this.stockId = stockId;
		this.data = data;
		this.price = price;
	}
}
