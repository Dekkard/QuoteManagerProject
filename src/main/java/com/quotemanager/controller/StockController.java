package com.quotemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotemanager.model.Stock;
import com.quotemanager.repository.StockService;

@RestController

@RequestMapping("/stockcache")
public class StockController {

	@Autowired
	StockService ss;

	@GetMapping("/register")
	public ResponseEntity<HttpStatus> registerApi(){
		return new ResponseEntity<>(HttpStatus.resolve(ss.register()));
	}
	
	@GetMapping
	public ResponseEntity<List<Stock>> getStockCache() {
		List<Stock> listStock = ss.list();
		if (!listStock.isEmpty())
			return new ResponseEntity<>(ss.list(), HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@PostMapping
	public ResponseEntity<HttpStatus> postStock(@RequestBody @Validated Stock stock) {
		int code = ss.addStock(stock);
		return new ResponseEntity<>(HttpStatus.resolve(code));
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteStockCache() {
		ss.empty();
		if (ss.list().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
