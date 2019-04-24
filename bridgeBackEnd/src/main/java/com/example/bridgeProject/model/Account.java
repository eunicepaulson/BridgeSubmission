package com.example.bridgeProject.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Account {
	
	@Id
	private String username;
	private String password;
	private List<String> stocks;
	Account(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.stocks = new ArrayList<String>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	
	public boolean addStock(String stockIndex)
	{
		if(!stocks.contains(stockIndex))
		{stocks.add(stockIndex);
			return true;
		}
		else
			return false;
	}
	
	public boolean deleteStock(String stockIndex)
	{
		int index =  stocks.indexOf(stockIndex);
		if(index != -1)
		{
		stocks.remove(index);
		return true;
		}
		else
			return false;
	}
	public List<String> getStocks()
	{
		return stocks;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString()
	{
		return "Username: "+username+" Password: "+password;
	}

}
