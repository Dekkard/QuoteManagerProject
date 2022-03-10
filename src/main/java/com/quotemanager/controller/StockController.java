package com.quotemanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotemanager.model.Stock;

@RestController
@RequestMapping("/stockcache")
public class StockController {

	@GetMapping
	public ResponseEntity<List<Stock>> getStockCache() {
		List<Stock> StockList = Stock.StockList();
		if (StockList.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(StockList, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteStockCache() {
		List<Stock> StockList = Stock.StockList();
		if (StockList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			StockList = Stock.RemoveStockList();
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
