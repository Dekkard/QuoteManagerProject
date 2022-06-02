package com.quotemanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotemanager.model.Quote;
import com.quotemanager.model.Stock;
import com.quotemanager.model.StockQuote;
import com.quotemanager.model.DTO.StockQuoteDTO;
import com.quotemanager.repository.QuoteService;
import com.quotemanager.repository.StockQuoteService;
import com.quotemanager.repository.StockService;

@RestController
@RequestMapping("/quote")
public class StockQuoteController {

	@Autowired
	StockQuoteService sqs;
	@Autowired
	QuoteService qs;
	@Autowired
	StockService ss;

	@GetMapping
	public ResponseEntity<List<StockQuoteDTO>> getStock() {
		List<StockQuote> listQuotes = sqs.list();
		List<StockQuoteDTO> listQuoteDTO = listQuotes.stream()//
				.map(StockQuoteDTO::ModelToDTO)//
				.collect(Collectors.toList());
		if (!listQuoteDTO.isEmpty())
			return new ResponseEntity<>(listQuoteDTO, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@GetMapping("{stockId}")
	public ResponseEntity<StockQuoteDTO> getStockById(@PathVariable("stockId") String stockId) {
		Optional<StockQuote> stockQuote = sqs.find(stockId);
		if (stockQuote.isPresent()) {
			return new ResponseEntity<>(StockQuoteDTO.ModelToDTO(stockQuote.get()), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping()
	public ResponseEntity<HttpStatus> postStock(@RequestBody @Validated StockQuoteDTO stockDTO) {
		StockQuote stockQuote = StockQuote.DTOToModel(stockDTO);
		List<Quote> listQuote = new ArrayList<>(stockQuote.getQuotes());
		List<Stock> listStock = ss.list();
		Optional<Stock> stockOpt = listStock.stream()//
				.filter(s -> s.getId().equals(stockQuote.getStockId()))//
				.findFirst();
		if (stockOpt.isPresent()) {
			Optional<StockQuote> sOpt = sqs.findByStockId(stockOpt.get().getId());
			if (sOpt.isPresent())
				sOpt = sqs.update(sOpt.get().getId(), stockQuote);
			else
				sOpt = sqs.insert(stockQuote);
			if (sOpt.isPresent()) {
				StockQuote[] s = { sOpt.get() };
				listQuote.stream().forEach(quote -> {
					quote.setStockQuote(s[0]);
					qs.insert(quote);
				});
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}