package com.quotemanager.model;

/**
 * <b>Stock:<b> Model class that describes the Stock, imported from another
 * application
 * <h6 style="margin-top:5px;margin-bottom:5px">Attributes</h6>
 * <ul>
 * <li><b>id:</b> Alfanumeric id, set by the Stock Manager application.</li>
 * <li><b>description:</b> Brief description of the Stock</li>
 * </ul>
 */
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

	/**
	 * <b>toJson(): Method used to parse the attributes of the Stock into Json
	 * format.</b>
	 */
	public String toJson() {
		// {"id":"id","description":"description"}
		return "{\"id\":\"" + id + "\",\"description\":\"" + description + "\"}";
	}
}
