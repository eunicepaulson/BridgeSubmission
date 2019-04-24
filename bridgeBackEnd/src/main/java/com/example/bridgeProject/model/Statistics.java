package com.example.bridgeProject.model;

public class Statistics {

	private String prices;
	private String open;
	private String low;
	private String high;
	private String prevClose;
	private String fiftyTwoWeekHigh;
	private String fiftyTwoWeekLow;
	private String volume;
	private String marketCap;
	private String name;
	private String dates;

	public Statistics(String prices, String open, String low, String high, String prevClose,
			String fiftyTwoWeekHigh, String fiftyTwoWeekLow, String volume, String marketCap, String name,
			String dates) {
		super();
		this.prices = prices;
		this.open = open;
		this.low = low;
		this.high = high;
		this.prevClose = prevClose;
		this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
		this.fiftyTwoWeekLow = fiftyTwoWeekLow;
		this.volume = volume;
		this.marketCap = marketCap;
		this.name = name;
		this.dates = dates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(String prevClose) {
		this.prevClose = prevClose;
	}

	public String getFiftyTwoWeekHigh() {
		return fiftyTwoWeekHigh;
	}

	public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
		this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
	}

	public String getFiftyTwoWeekLow() {
		return fiftyTwoWeekLow;
	}

	public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
		this.fiftyTwoWeekLow = fiftyTwoWeekLow;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}

}

