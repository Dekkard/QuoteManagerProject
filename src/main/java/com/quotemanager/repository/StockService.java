package com.quotemanager.repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.quotemanager.model.Notification;
import com.quotemanager.model.Stock;

@Service
@Component
public class StockService {
	private final static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	List<Stock> listStock = new ArrayList<>();

	@Cacheable(value = "StockList")
	public List<Stock> list() {
//		register();
		try {
			HttpRequest hr = HttpRequest.newBuilder()//
					.GET()//
					.uri(new URI("http://localhost:8084/stock"))//
					.headers("User-Agente", "JavaApp", "Content-Type", "application/json").build();
			HttpResponse<String> response = httpClient.send(hr, BodyHandlers.ofString());
			JSONArray jsonArray = new JSONArray(response.body());
			jsonArray.forEach(obj -> {
				JSONObject jObj = new JSONObject(obj.toString());
				String id = jObj.getString("id");
				String description = jObj.getString("description");
				listStock.add(new Stock(id, description));
			});
			System.out.println("Stock entries uploaded.");
			return listStock;
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int addStock(Stock stock) {
		try {
			HttpRequest hr = HttpRequest.newBuilder()//
					.uri(new URI("http://localhost:8084/stock"))//
					.POST(HttpRequest.BodyPublishers.ofString(stock.toString()))//
					.headers("User-Agente", "JavaApp", "Content-Type", "application/json").build();
			HttpResponse<String> response = httpClient.send(hr, HttpResponse.BodyHandlers.ofString());
			listStock.add(stock);
			return response.statusCode();
		} catch (URISyntaxException | IOException | InterruptedException e) {
			return 400;
		}
	}

	@CacheEvict(value = "StockList", allEntries = true)
	public void empty() {
		listStock.clear();
	}

	public int register() {
		try {
			Notification not = new Notification("localhost", 8081);
			HttpRequest hr = HttpRequest.newBuilder()//
					.uri(new URI("http://localhost:8084/notification"))//
					.POST(HttpRequest.BodyPublishers.ofString(not.toString()))//
					.headers("User-Agente", "JavaApp", "Content-Type", "application/json")//
					.build();
			HttpResponse<String> response = httpClient.send(hr, HttpResponse.BodyHandlers.ofString());
			System.out.println("Server has been notified.");
			int code = response.statusCode();
			if (code >= 300) {
				System.out.println("However...");
				System.out.println(response.body());
			}
			return code;
		} catch (URISyntaxException | IOException | InterruptedException e) {
			System.out.println("The server has not been notified.");
			return 400;
		}
	}
}
