package com.example.bridgeProject.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SavedStock extends CurrentStock implements Serializable {

	@Id
	private String id;
	private String username;
	private int noOfShares;
	private String dateSaved;
	private double profit;

	public SavedStock(String symbol, String name, double nsePrice, double bsePrice, String buyInExchange,
			double percentageDifference, String username, int noOfShares, String dateSaved, double profit) {
		super(symbol, name, nsePrice, bsePrice, buyInExchange, percentageDifference);
		this.username = username;
		this.noOfShares = noOfShares;
		this.dateSaved = dateSaved;
		this.profit = profit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(int noOfShares) {
		this.noOfShares = noOfShares;
	}

	public String getDateSaved() {
		return dateSaved;
	}

	public void setDateSaved(String dateSaved) {
		this.dateSaved = dateSaved;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public String toString() {
		return "Id:"+id+"Symbol:" + symbol + "Name: " + name + "NSE Price: " + nsePrice + "BSE Price: " + bsePrice
				+ "Buy in Exchange: " + buyInExchange + "Percentage Difference:" + percentageDifference
				+ "No Of Shares:" + noOfShares + "Date Saved: " + dateSaved;
	}

}
