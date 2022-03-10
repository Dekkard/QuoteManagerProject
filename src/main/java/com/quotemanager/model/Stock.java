package com.quotemanager.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Stock {
	private String id;
	private String description;

	@Cacheable("StockList")
	public static List<Stock> StockList() {
		String uri = "http://localhost:8080/stock/";
		RestTemplate rt = new RestTemplate();
		ResponseEntity<Stock[]> re = rt.getForEntity(uri, Stock[].class);
		Stock[] stocks = re.getBody();
		List<Stock> stockList = Arrays.stream(stocks).collect(Collectors.toList());
		return stockList;
	}
	
	@CacheEvict(value="StockList", allEntries = true)
	public static List<Stock> RemoveStockList(){
		return new ArrayList<>();
	}
}
