package com.quotemanager.model.DTO;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class Message {
	private LocalDateTime timestamp;
	private Integer status;
	private String reason;
	private String message;

	public Message() {
		super();
	}

	public Message(LocalDateTime timestamp, Integer status, String reason, String message) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.reason = reason;
		this.message = message;
	}

	public Message(LocalDateTime timestamp, HttpStatus status, String message) {
		super();
		this.timestamp = timestamp;
		this.status = status.value();
		this.reason = status.getReasonPhrase();
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public String getReason() {
		return reason;
	}

	public String getMessage() {
		return message;
	}

}
