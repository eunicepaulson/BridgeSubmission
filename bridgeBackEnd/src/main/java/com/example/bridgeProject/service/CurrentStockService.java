package com.example.bridgeProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bridgeProject.model.CurrentStock;
import com.example.bridgeProject.model.StockIndex;
import com.example.bridgeProject.repository.StockIndexRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class CurrentStockService {

	private Logger logger = LogManager.getLogger(CurrentStockService.class);
	private List<List<String>> stockIndices = new ArrayList<List<String>>();
	boolean stockIndicesSet;
	@Autowired
	API api;

	@Autowired
	StockIndexRepository stockIndexRepository;

	// The constructor reads the indexes from MongoDB file and makes a set of lists
	// of these indices
	protected CurrentStockService() {
		boolean stockIndicesSet = false;
	}

	public List<List<String>> setStockIndices() {
		List<StockIndex> response = stockIndexRepository.findAll();
		List<String> indices = new ArrayList<String>();
		for (int i = 0; i < response.size(); i++)
			indices.add(response.get(i).getIndex());

		stockIndices = makeList(indices, 163);
		return stockIndices;
	}

	public List<List<String>> makeList(List<String> stockIndex, int n) {
		List<List<String>> indices = new ArrayList<List<String>>();
		int i = 0;
		while ((i + n) < stockIndex.size()) {
			List<String> stockIndexes = stockIndex.subList(i, i + n);
			indices.add(stockIndexes);
			i += n;
		}
		List<String> stockIndexes = stockIndex.subList(i, stockIndex.size());
		indices.add(stockIndexes);
		return indices;
	}

	// The returns the valid set of recommendations based on a certain percentage
	// Difference threshold (0.5)
	public List<CurrentStock> getRecommendations() {
		logger.info("START ALL RECOMMENDATIONS");
		long start = System.currentTimeMillis();
		if (!stockIndicesSet) {
			setStockIndices();
			stockIndicesSet = true;
		}
		List<CurrentStock> stocksToBeDisplayed = new ArrayList<CurrentStock>();
		// get the list of stocks to be displayed
		stocksToBeDisplayed = callAPI(stockIndices, true);

		// sort list based on descending order of percentage Difference
		Collections.sort(stocksToBeDisplayed, new SortByPercentage());
		logger.info(" DONE FETCHING RECOMMENDATIONS" + (System.currentTimeMillis() - start));
		return stocksToBeDisplayed;

	}

	// returns the data for those stocks the user has selected (no filtering
	// applied)
	public List<CurrentStock> getUserRecommendations(List<String> stockIndex) {
		logger.info("START ALL USER RECOMMENDATIONS");
		long start = System.currentTimeMillis();
		List<CurrentStock> stocksToBeDisplayed = new ArrayList<CurrentStock>();
		List<List<String>> indices = makeList(stockIndex, 50);
		stocksToBeDisplayed = callAPI(indices, false);
		logger.info(" DONE FETCHING USER RECOMMENDATIONS" + (System.currentTimeMillis() - start));
		return stocksToBeDisplayed;
	}

	// Taking a list of strings, this method returns the required stock data for all
	// the lists
	public List<CurrentStock> callAPI(List<List<String>> listOfStocks, boolean filter) {
		try {
			List<CompletableFuture<JSONObject>> calls = new ArrayList<CompletableFuture<JSONObject>>();
			List<CurrentStock> stocksToBeDisplayed = new ArrayList<CurrentStock>();
			// Get Api Response
			for (int i = 0; i < listOfStocks.size(); i++) {
				if (listOfStocks.get(i).size() > 0) {
					String url = generateUrl(listOfStocks.get(i));
					calls.add(api.getAPIResponse(url));
				}
			}
			// Extract the required data for each stock from the response
			for (int i = 0; i < calls.size(); i++)
				stocksToBeDisplayed.addAll(extractStockData(calls.get(i).get(), filter));
			return stocksToBeDisplayed;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Generate the required URL for get-quotes based on the list of companies
	// passed
	public String generateUrl(List<String> stockIndex) {
		String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-quotes?region=IN&lang=en&symbols=";
		url += stockIndex.get(0) + ".NS%2C" + stockIndex.get(0) + ".BO";
		for (int i = 1; i < stockIndex.size(); i++) {
			url += "%2C" + stockIndex.get(i) + ".NS%2C" + stockIndex.get(i) + ".BO";
		}
		return url;
	}

	// Get the required data for a set of stocks using the API response
	public List<CurrentStock> extractStockData(JSONObject apiResponse, boolean filter) {
		List<CurrentStock> stocksToBeDisplayed = new ArrayList<CurrentStock>();
		if (apiResponse != null) {
			JSONArray result = apiResponse.getJSONObject("quoteResponse").getJSONArray("result");
			for (int i = 0; i + 1 < result.length(); i += 2) {
				boolean flag = true;
				// get NSE and BSE data
				JSONObject NSE = (JSONObject) result.get(i);
				JSONObject BSE = (JSONObject) result.get(i + 1);
				double NSEPrice = NSE.getDouble("regularMarketPrice");
				double BSEPrice = BSE.getDouble("regularMarketPrice");
				String buy;
				double percentageDifference;
				if (NSEPrice < BSEPrice) {
					buy = "NSE";
					percentageDifference = Math.round((((BSEPrice - NSEPrice) / NSEPrice) * 100) * 100.0) / 100.0;
				} else {
					buy = "BSE";
					percentageDifference = Math.round((((NSEPrice - BSEPrice) / BSEPrice) * 100) * 100.0) / 100.0;
				}

				// filters if percentage difference is greater than a certain threshold
				if (filter) {
					if (percentageDifference > 0.5)
						flag = true;
					else
						flag = false;
				}
				if (flag) {
					// Add the stock to the list for displaying
					String symbol = NSE.getString("symbol");
					symbol = symbol.substring(0, symbol.lastIndexOf('.'));
					String name = NSE.getString("longName");
					NSEPrice = Math.round(NSEPrice * 100.0) / 100.0;
					BSEPrice = Math.round(BSEPrice * 100.0) / 100.0;
					stocksToBeDisplayed
							.add(new CurrentStock(symbol, name, NSEPrice, BSEPrice, buy, percentageDifference));
				}
			}
		}
		return stocksToBeDisplayed;

	}

	// returns true if the market is open otherwise false
	public boolean getMarketState() {
		try {
			JSONObject response = api.getAPIResponse(
					"https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-quotes?region=IN&lang=en&symbols=INFY.NS")
					.get();
			String state = response.getJSONObject("quoteResponse").getJSONArray("result").getJSONObject(0)
					.getString("marketState");
			if (state.equals("REGULAR"))
				return true;
			else
				return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

}

//Class for sorting the stocks that will be displayed
class SortByPercentage implements Comparator<CurrentStock> {

	// sort according to descending order of percentageDifference
	public int compare(CurrentStock a, CurrentStock b) {
		if (a.getPercentageDifference() < b.getPercentageDifference())
			return 1;

		else if (a.getPercentageDifference() > b.getPercentageDifference())
			return -1;
		else
			return 0;
	}
}
