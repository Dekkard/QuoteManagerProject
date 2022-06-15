package com.quotemanager.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
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
import com.quotemanager.model.DTO.Message;
import com.quotemanager.model.DTO.StockQuoteDTO;
import com.quotemanager.service.QuoteService;
import com.quotemanager.service.StockQuoteService;
import com.quotemanager.service.StockService;

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
		List<StockQuote> listStockQuotes = sqs.list();
		/*
		 * List<Stock> stocks = listStockQuotes.stream().map(stockQuote -> new
		 * Stock(stockQuote.getStockId(), "")) .filter(stock ->
		 * !ss.list().stream().anyMatch(s -> s.getId().equals(stock.getId())))
		 * .collect(Collectors.toList()); stocks.forEach(s -> ss.addStock(s));
		 */
//		listStockQuotes.stream().forEach(stock -> stock.setQuotes(stock.getQuotes().stream().sorted(Comparator.comparing(Quote::getDate)).collect(Collectors.toList())));
		List<StockQuoteDTO> listQuoteDTO = listStockQuotes.stream()//
				.map(StockQuoteDTO::ModelToDTO)//
				.collect(Collectors.toList());
		listQuoteDTO.forEach(StockQuoteDTO::sortQuotes);
		if (!listQuoteDTO.isEmpty())
			return new ResponseEntity<>(listQuoteDTO, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("{id}")
	public ResponseEntity<StockQuoteDTO> getStockById(@PathVariable("id") String id) {
		Optional<StockQuote> stockQuote = Optional.empty();
		try {
			UUID.fromString(id);
			stockQuote = sqs.find(id);
		} catch (IllegalArgumentException e) {
			stockQuote = sqs.findByStockId(id);
		}
		if (stockQuote.isPresent()) {
			StockQuoteDTO stockQuoteDTO = StockQuoteDTO.ModelToDTO(stockQuote.get());
			stockQuoteDTO.sortQuotes();
			return new ResponseEntity<>(stockQuoteDTO, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping()
	public ResponseEntity<Message> postStock(@RequestBody @Validated StockQuoteDTO stockDTO) {
		try {
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
					final StockQuote fStockQuote = sOpt.get();
					listQuote.stream().forEach(quote -> {
						quote.setStockQuote(fStockQuote);
						qs.insert(quote);
					});
					return new ResponseEntity<>(HttpStatus.CREATED);
				}
			} else
				return new ResponseEntity<>(new Message(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Stock does not exist."),
						HttpStatus.NOT_FOUND);
		} catch (ConstraintViolationException e) {
			String msg = ((ConstraintViolationImpl<ConstraintViolation>) e.getConstraintViolations().toArray()[0])
					.getMessage();
			return new ResponseEntity<>(new Message(LocalDateTime.now(), HttpStatus.BAD_REQUEST, msg),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}