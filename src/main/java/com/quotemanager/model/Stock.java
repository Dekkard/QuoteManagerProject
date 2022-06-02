package com.quotemanager.model;

public class Stock {
	private String id;
	private String description;

	public Stock() {
		super();
	}

	public Stock(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return id + ": " + description;
	}
}
