package com.quotemanager.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotemanager.model.Notification;
import com.quotemanager.model.Stock;
import com.quotemanager.model.DTO.Message;
import com.quotemanager.service.StockService;

@RestController

@RequestMapping("/")
public class StockController {

	@Autowired
	StockService ss;

	@PostMapping("stockcache/register")
	public ResponseEntity<HttpStatus> registerApi(@RequestBody @Nullable Notification notification) {
		return new ResponseEntity<>(HttpStatus.resolve(ss.register(notification)));
	}

	@GetMapping("stockcache")
	public ResponseEntity<List<Stock>> getStockCache() {
		List<Stock> listStock = ss.list();
		if (!listStock.isEmpty())
			return new ResponseEntity<>(listStock, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("stock")
	public ResponseEntity<Message> postStock(@RequestBody @Validated Stock stock) {
		try {
			return new ResponseEntity<>(HttpStatus.resolve(ss.addStock(stock)));
		} catch (ConstraintViolationException e) {
			String msg = ((ConstraintViolationImpl<ConstraintViolation>) e.getConstraintViolations().toArray()[0])
					.getMessage();
			return new ResponseEntity<>(new Message(LocalDateTime.now(), 400, "Bad Request", msg),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(LocalDateTime.now(), HttpStatus.BAD_GATEWAY, e.getLocalizedMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@DeleteMapping("stockcache")
	public ResponseEntity<Message> deleteStockCache() {
		try {
			if (ss.empty()) {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(
						new Message(LocalDateTime.now(), HttpStatus.BAD_GATEWAY, "Server not responding."),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(LocalDateTime.now(), HttpStatus.BAD_GATEWAY, e.getLocalizedMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
