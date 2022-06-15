package com.quotemanager.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.quotemanager.http.HttpRequestAdapter;
import com.quotemanager.model.Notification;
import com.quotemanager.model.Stock;

@Service
@Component
public class StockService {
	List<Stock> listStock = new ArrayList<>();
	private static HttpRequestAdapter hrp = new HttpRequestAdapter();
	@Value("${server.port}")
	Integer port;

	@Value("${server.host}")
	String host;

	@Value("${coserver.port}")
	Integer coport;

	@Value("${coserver.host}")
	String cohost;

	@Cacheable(value = "StockList")
	public List<Stock> list() {
		hrp.getRequest("http://" + cohost + ":" + coport + "/stock", jObj -> {
			String id = jObj.getString("id");
			String description = jObj.getString("description");
			listStock.add(new Stock(id, description));
		});
		System.out.println("Stock entries uploaded.");
		return listStock;
	}

	@CacheEvict(value = "StockList", allEntries = true)
	public int addStock(@Validated Stock stock) {
		try {
			HttpResponse<String> response = hrp.postRequest("http://" + cohost + ":" + coport + "/stock", stock.toJson());
			return response.statusCode();
		} catch (URISyntaxException | IOException | InterruptedException e) {
			return 400;
		}
	}

	@CacheEvict(value = "StockList", allEntries = true)
	public boolean empty() {
		listStock.clear();
		return listStock.isEmpty();
	}

	public int register(Notification not) {
		try {
			Notification notification = new Notification();
			try {
				if(!not.getHost().equals(null)&&!not.getPort().equals(null))
					notification = not;
			} catch(NullPointerException e) {
				notification = new Notification(host, port);
			}
			HttpResponse<String> response = hrp.postRequest("http://" + cohost + ":" + coport + "/notification", notification.toJson());
			int code = response.statusCode();
			System.out.println("Server has been notified.");
			if (code >= 300) {
				System.out.println("However...");
				hrp.parseJsonObject(response.body());
			}
			return code;
		} catch (URISyntaxException | IOException | InterruptedException e) {
			System.out.println("The server has not been notified.");
			return 400;
		}
	}
}
