package com.quotemanager.model;

/**
 * <b>Notification:</b> Auxiliary class that's used to transfer a notification
 * to the Stock Manager Application.
 * <h6 style="margin-top:5px;margin-bottom:5px">Attributes</h6>
 * <ul>
 * <li><b>host:</b> Ip address or domain the Quote Manager is being executed.</li>
 * <li><b>port:</b> The port in which de application can be accessed</li>
 * </ul>
 */
public class Notification {
	private String host;
	private Integer port;

	public Notification() {
	}
	
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
		return host + ":" + port;
	}

	/**
	 * <b>toJson(): Method used to parse the attributes of the Stock into Json
	 * format.</b>
	 */
	public String toJson() {
//		{"host":"host":"port":"port"}
		return "{\"host\":\"" + host + "\",\"port\":\"" + port + "\"}";
	}
}
