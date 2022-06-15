package com.quotemanager.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpRequestAdapter {

	public interface JsonParser {
		public void method(JSONObject jObj);
	}

	private final static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	public <E> void getRequest(String uri, JsonParser jsonParser) {
		try {
			HttpRequest hr = HttpRequest.newBuilder()//
					.GET()//
					.uri(new URI(uri))//
					.headers("User-Agente", "JavaApp", "Content-Type", "application/json")//
					.build();
			HttpResponse<String> response = httpClient.send(hr, BodyHandlers.ofString());
			parseJsonObject(response.body(), jsonParser);
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public <E> HttpResponse<String> postRequest(String uri, String jsonObj)
			throws IOException, InterruptedException, URISyntaxException {
		HttpRequest hr = HttpRequest.newBuilder()//
				.uri(new URI(uri))//
				.POST(HttpRequest.BodyPublishers.ofString(jsonObj))//
				.headers("User-Agente", "JavaApp", "Content-Type", "application/json")//
				.build();
		HttpResponse<String> response = httpClient.send(hr, BodyHandlers.ofString());
		return response;
	}

	public void parseJsonObject(String response, JsonParser parser) {
		JSONArray jsonArray = new JSONArray(response);
		jsonArray.forEach(obj -> {
			JSONObject jObj = new JSONObject(obj.toString());
			parser.method(jObj);
		});
	}

	public void parseJsonObject(String response) {
		JSONArray jsonArray = new JSONArray(response);
		jsonArray.forEach(obj -> {
			JSONObject jObj = new JSONObject(obj.toString());
			jObj.keySet().stream().forEach(j -> System.out.println(jObj.get(j)));
		});
	}
}
