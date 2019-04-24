package com.example.bridgeProject.model;

public class CurrentStock {

	protected String symbol;
	protected String name;
	protected double nsePrice;
	protected double bsePrice;
	protected String buyInExchange;
	protected double percentageDifference;
	public CurrentStock(String symbol, String name, double nsePrice, double bsePrice, String buyInExchange,
			double percentageDifference) {
		this.symbol = symbol;
		this.name = name;
		this.nsePrice = nsePrice;
		this.bsePrice = bsePrice;
		this.buyInExchange = buyInExchange;
		this.percentageDifference = percentageDifference;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getNsePrice() {
		return nsePrice;
	}
	public void setNsePrice(double nsePrice) {
		this.nsePrice = nsePrice;
	}
	public double getBsePrice() {
		return bsePrice;
	}
	public void setBsePrice(double bsePrice) {
		this.bsePrice = bsePrice;
	}
	public String getBuyInExchange() {
		return buyInExchange;
	}
	public void setBuyInExchange(String buyInExchange) {
		this.buyInExchange = buyInExchange;
	}
	public double getPercentageDifference() {
		return percentageDifference;
	}
	public void setPercentageDifference(double percentageDifference) {
		this.percentageDifference = percentageDifference;
	}
	
	public String toString()
	{
		return "Symbol:"+symbol+"Name: "+name+"NSE Price: "+nsePrice+"BSE Price: "+bsePrice+"Buy in Exchange: "+buyInExchange+"Percentage Difference:"+percentageDifference;
	}
}

