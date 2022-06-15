package com.quotemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.quotemanager.controller.StockController;
import com.quotemanager.controller.StockQuoteController;
import com.quotemanager.model.Quote;
import com.quotemanager.model.Stock;
import com.quotemanager.model.StockQuote;
import com.quotemanager.model.DTO.Message;
import com.quotemanager.model.DTO.QuoteDTO;
import com.quotemanager.model.DTO.StockQuoteDTO;

@SpringBootTest
class QuoteManagerApplicationTests {

	@Autowired
	private StockController sc;

	@Autowired
	private StockQuoteController sqc;

	@Test
	void convertQuote() {
		Quote quote = new Quote(1l, LocalDate.of(2022, Month.JUNE, 7), new BigDecimal("11.11"), new StockQuote());
		QuoteDTO quoteDTO = QuoteDTO.ModeltoDTO(quote);
		Quote quote2 = Quote.DTOtoModel(quoteDTO);
		assertEquals(quote2, quote);
	}

	@Test
	void convertStockQuote() {
		List<Quote> listQuote = new ArrayList<>();
		StockQuote stockQuote = new StockQuote(UUID.randomUUID().toString(), "stock1", listQuote);
		listQuote.add(new Quote(1l, LocalDate.of(2022, Month.JUNE, 6), new BigDecimal("11.11"), stockQuote));
		listQuote.add(new Quote(1l, LocalDate.of(2022, Month.JUNE, 7), new BigDecimal("12.21"), stockQuote));
		StockQuoteDTO stockQuoteDTO = StockQuoteDTO.ModelToDTO(stockQuote);
		StockQuote stockQuote2 = StockQuote.DTOToModel(stockQuoteDTO);
		assertEquals(stockQuote2, stockQuote);
	}

	@Test
	void getStocks() {
		ResponseEntity<List<Stock>> resp = sc.getStockCache();
		HttpStatus status = resp.getStatusCode();
		List<Stock> listStock = resp.getBody();
		listStock.stream().forEach(System.out::println);
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	void postStock() {
		Stock stock = new Stock("stock1", "Stock test 1");
		ResponseEntity<Message> resp = sc.postStock(stock);
		HttpStatus status = resp.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	@Transactional
	void getStockQuote() {
		ResponseEntity<List<StockQuoteDTO>> resp = sqc.getStock();
		HttpStatus status = resp.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	void postQuoteStock() {
		List<Quote> listQuote = new ArrayList<>();
		StockQuote sq = new StockQuote(UUID.randomUUID().toString(), "petr4", listQuote);
		listQuote.add(new Quote(LocalDate.of(2022, Month.JUNE, 6), new BigDecimal("11.11"), sq));
		listQuote.add(new Quote(LocalDate.of(2022, Month.JUNE, 7), new BigDecimal("12.21"), sq));
		listQuote.add(new Quote(LocalDate.of(2022, Month.JUNE, 8), new BigDecimal("13.91"), sq));
		ResponseEntity<Message> resp = sqc.postStock(StockQuoteDTO.ModelToDTO(sq));
		HttpStatus status = resp.getStatusCode();
		assertEquals(HttpStatus.CREATED, status);
	}

	@Test
	void postQuoteStockNotExists() {
		List<Quote> listQuote = new ArrayList<>();
		StockQuote sq = new StockQuote(UUID.randomUUID().toString(), "petr5", listQuote);
		listQuote.add(new Quote(LocalDate.of(2022, Month.JUNE, 6), new BigDecimal("15.11"), sq));
		listQuote.add(new Quote(LocalDate.of(2022, Month.JUNE, 7), new BigDecimal("16.21"), sq));
		ResponseEntity<Message> resp = sqc.postStock(StockQuoteDTO.ModelToDTO(sq));
		HttpStatus status = resp.getStatusCode();
		assertEquals(HttpStatus.NOT_FOUND, status);
	}

	@Test
	void postQuoteStockWrongFormat() {
		assertThrows(DateTimeParseException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			StockQuote sq = new StockQuote(UUID.randomUUID().toString(), "petr4", listQuote);
			listQuote.add(new Quote(LocalDate.of(2022, Month.MARCH, 30), new BigDecimal("15.11"), sq));
			listQuote.add(new Quote(LocalDate.parse("2022-3-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
					new BigDecimal("16.21"), sq));
			ResponseEntity<Message> resp = sqc.postStock(StockQuoteDTO.ModelToDTO(sq));
			HttpStatus status = resp.getStatusCode();
			assertEquals(HttpStatus.BAD_REQUEST, status);
		});
	}

	@Test
	void postQuoteStockWrongPriceFormat() {
		assertThrows(NumberFormatException.class, () -> {
			List<Quote> listQuote = new ArrayList<>();
			StockQuote sq = new StockQuote(UUID.randomUUID().toString(), "petr4", listQuote);
			listQuote.add(new Quote(LocalDate.of(2022, Month.MARCH, 30), new BigDecimal("15.11"), sq));
			listQuote.add(new Quote(LocalDate.of(2022, Month.MARCH, 31), new BigDecimal("16.21m"), sq));
			ResponseEntity<Message> resp = sqc.postStock(StockQuoteDTO.ModelToDTO(sq));
			HttpStatus status = resp.getStatusCode();
			assertEquals(HttpStatus.BAD_REQUEST, status);
		});
	}

	@Test
	void postQuoteStockWrongPriceNegative() {
		List<Quote> listQuote = new ArrayList<>();
		StockQuote sq = new StockQuote(UUID.randomUUID().toString(), "petr4", listQuote);
		listQuote.add(new Quote(LocalDate.of(2022, Month.MARCH, 30), new BigDecimal("15.11"), sq));
		listQuote.add(new Quote(LocalDate.of(2022, Month.MARCH, 31), new BigDecimal("-16.21"), sq));
		ResponseEntity<Message> resp = sqc.postStock(StockQuoteDTO.ModelToDTO(sq));
		HttpStatus status = resp.getStatusCode();
		assertEquals(HttpStatus.BAD_REQUEST, status);
	}

	@Test
	@Transactional
	void getStockQuoteById() {
		ResponseEntity<StockQuoteDTO> resp = sqc.getStockById("petr4");
		HttpStatus status = resp.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	@Transactional
	void getStockQuoteByIdButDoesNotExists() {
		ResponseEntity<StockQuoteDTO> resp = sqc.getStockById("petr5");
		HttpStatus status = resp.getStatusCode();
		assertEquals(HttpStatus.NOT_FOUND, status);
	}
}
