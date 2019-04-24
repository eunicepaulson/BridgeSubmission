package com.example.bridgeProject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bridgeProject.model.Statistics;

@Service
public class StatisticsService  {

	@Autowired
	API api;

	//Returns the NSE and BSE Statistics based on the stock Index provided
	public List<Statistics> getStatistics(String stockIndex) {
		try {
			List<Statistics> stats = new ArrayList<Statistics>();
			List<CompletableFuture<JSONObject>> calls = new ArrayList<CompletableFuture<JSONObject>>();
			
			//Calls to the API
			calls.add(api.getAPIResponse(
					"https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?region=IN&lang=en&symbol="
							+ stockIndex + ".NS"));
			calls.add(api.getAPIResponse(
					"https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?region=IN&lang=en&symbol="
							+ stockIndex + ".BO"));
			calls.add(
					api.getAPIResponse("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts?comparisons="+stockIndex+".BO&region=IN&lang=en&symbol=" + stockIndex + ".NS&interval=1d&range=3mo"));
		
			
			//Using the response from the API, required details are extracted
			Statistics NSE = extractStatistics(calls.get(0).get());
			Statistics BSE = extractStatistics(calls.get(1).get());
			JSONObject responseBody = calls.get(2).get();
			JSONArray BSEPrices = responseBody.getJSONObject("chart").getJSONArray("result").getJSONObject(0)
				.getJSONArray("comparisons").getJSONObject(0).getJSONArray("close");
			JSONArray NSEPrices = responseBody.getJSONObject("chart").getJSONArray("result").getJSONObject(0)
					.getJSONObject("indicators").getJSONArray("adjclose").getJSONObject(0).getJSONArray("adjclose");
			JSONArray dates = responseBody.getJSONObject("chart").getJSONArray("result").getJSONObject(0)
					.getJSONArray("timestamp");
			NSE.setPrices(NSEPrices.toString());
			BSE.setPrices(BSEPrices.toString());
			NSE.setDates(dates.toString());
			stats.add(NSE);
			stats.add(BSE);
			return stats;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Extracts the required fields for displaying statistics from the JSONObject
	public Statistics extractStatistics(JSONObject apiResponse) {
		Statistics obj;
		String name = apiResponse.getJSONObject("quoteType").getString("longName");
		apiResponse = apiResponse.getJSONObject("summaryDetail");
		String fiftyTwoWeekHigh = apiResponse.getJSONObject("fiftyTwoWeekHigh").getString("fmt");
		String fiftyTwoWeekLow = apiResponse.getJSONObject("fiftyTwoWeekLow").getString("fmt");
		String open = apiResponse.getJSONObject("open").getString("fmt");
		String low = apiResponse.getJSONObject("dayLow").getString("fmt");
		String prevClose = apiResponse.getJSONObject("previousClose").getString("fmt");
		String high = apiResponse.getJSONObject("dayHigh").getString("fmt");
		String marketCap = apiResponse.getJSONObject("marketCap").getString("fmt");
		String volume = apiResponse.getJSONObject("volume").getString("fmt");

		obj = new Statistics(null, open, low, high, prevClose, fiftyTwoWeekHigh, fiftyTwoWeekLow, volume, marketCap,
				name, null);
		return obj;

	}
}