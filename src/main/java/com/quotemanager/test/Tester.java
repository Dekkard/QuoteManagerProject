package com.quotemanager.test;

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

import com.quotemanager.model.Stock;

public class Tester {
	private final static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	public static void main(String[] args) {
		List<Stock> listStock = new ArrayList<>();
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
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		listStock.forEach(System.out::println);
	}
}
