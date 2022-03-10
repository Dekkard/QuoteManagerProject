package com.quotemanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.quotemanager.model.QuoteDTO;
import com.quotemanager.model.Stock;
import com.quotemanager.model.StockQuote;
import com.quotemanager.model.StockQuoteDTO;
import com.quotemanager.repository.QuoteRepository;
import com.quotemanager.repository.StockQuoteRepository;

@RestController
@RequestMapping("/quote")
public class StockQuoteController {

	@Autowired
	StockQuoteRepository sqr;
	@Autowired
	QuoteRepository qr;

	@GetMapping
	public ResponseEntity<List<StockQuoteDTO>> getStock() {
		List<StockQuote> stocks = sqr.findAll();
		if (stocks.isEmpty()) {
			System.out.println("No stock found.");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			List<StockQuoteDTO> stocksDTO = new ArrayList<>();
			for (StockQuote s : stocks) {
				List<QuoteDTO> qDto = new ArrayList<>();
				List<Quote> quotes = qr.findByStockId(s.getStockId());
				for (Quote q : quotes) {
					qDto.add(new QuoteDTO(q.getData(), q.getPrice()));
				}
				stocksDTO.add(new StockQuoteDTO(s.getStockId(), s.getId(), qDto));
			}
			return new ResponseEntity<>(stocksDTO, HttpStatus.OK);
		}
	}

	@GetMapping("{stockId}")
	public ResponseEntity<StockQuoteDTO> getStockById(@PathVariable("stockId") String stockId) {
		Optional<StockQuote> stock = sqr.findByStockId(stockId);
		if (stock.isPresent()) {
			StockQuote s = stock.get();
			List<Quote> quotes = s.getQuotes();
			List<QuoteDTO> qDto = new ArrayList<>();
			for (Quote q : quotes) {
				qDto.add(new QuoteDTO(q.getData(), q.getPrice()));
			}
			return new ResponseEntity<>(new StockQuoteDTO(s.getStockId(), s.getId(), qDto), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/")
	public ResponseEntity<StockQuote> postStock(@RequestBody @Validated StockQuoteDTO stockDTO) {
//		String uri = "http://localhost:8080/stock/" + stockDTO.getStockId();
//		RestTemplate rt = new RestTemplate();
//		Stock stock = rt.getForObject(uri, Stock.class);
		List<Stock> StockList = Stock.StockList();
		for(Stock stock : StockList) {
			if (stock.getId().equals(stockDTO.getStockId())) {
				List<QuoteDTO> qDto = stockDTO.getQuotes();
				List<Quote> quotes = new ArrayList<>();
				for (QuoteDTO q : qDto) {
					Quote quote = new Quote(stockDTO.getStockId(), q.getData(), q.getPrice());
					qr.save(quote);
					quotes.add(quote);
				}
				StockQuote stockquote = new StockQuote(stockDTO.getId(), stockDTO.getStockId(), quotes);
				return new ResponseEntity<>(sqr.save(stockquote), HttpStatus.CREATED);
			} 
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}