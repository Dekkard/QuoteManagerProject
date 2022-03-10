package com.quotemanager.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockQuoteDTO {
	private String stockId;
	private String id;
	private List<QuoteDTO> quotes;
}