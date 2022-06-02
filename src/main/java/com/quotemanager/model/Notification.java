package com.quotemanager.model;

public class Notification {
	private String host;
	private Integer port;

	public Notification(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}
	
	@Override
	public String toString() {
//		{"host":"host":"port":"port"}
		return "{\"host\":\""+host+"\",\"port\":\""+port+"\"}";
	}
}
