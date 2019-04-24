package com.example.bridgeProject.service;
import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;

@Service
public class API {

	private Logger logger = LogManager.getLogger(CurrentStockService.class);
	
	//The method is used to fetch the response from the API
	@Async 
	public CompletableFuture<JSONObject> getAPIResponse(String url) {
		try {
			logger.info("BEFORE FETCHING");
			HttpResponse<JsonNode> response = Unirest.get(url)
					.header("X-RapidAPI-Key", "5bf0526984msheaca59397bd7438p154f84jsn3f64f80de14f").asJson();			
			JSONObject responseBody = response.getBody().getObject();
			logger.info("AFTER FETCHING");
			return CompletableFuture.completedFuture(responseBody);
		}
		catch(UnirestException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
}
