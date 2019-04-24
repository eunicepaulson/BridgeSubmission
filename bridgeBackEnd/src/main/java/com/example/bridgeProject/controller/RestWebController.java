package com.example.bridgeProject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bridgeProject.model.*;
import com.example.bridgeProject.service.*;

//Accepts requests starting with this url
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RestWebController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private SavedStockService savedStockService;
	@Autowired
	private CurrentStockService currentStockService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private StockIndexService stockIndexService;

	@GetMapping("/login/{username}/{password}")
	public Account validateUser(@PathVariable("username") String username, @PathVariable("password") String password) {
		Account result = accountService.validateUser(username, password);
		if (result != null)
			accountService.setLoggedUser(result);
		return result;
	}

	@GetMapping("/userLoggedIn")
	public boolean userLoggedIn() {
		if (accountService.getLoggedUser() != null)
			return true;
		else
			return false;
	}

	@GetMapping("/marketState")
	public boolean getMarketState() {
		return currentStockService.getMarketState();
	}

	@GetMapping("/getStatistics/{stockIndex}")
	public List<Statistics> getCharts(@PathVariable("stockIndex") String stockIndex) {
		return statisticsService.getStatistics(stockIndex);
	}

	@GetMapping("/getRecommendations")
	public List<CurrentStock> getRecommendations() {
		return currentStockService.getRecommendations();
	}

	@GetMapping("/getUserRecommendations")
	public List<CurrentStock> getUserRecommendations() {

		return currentStockService.getUserRecommendations(accountService.getLoggedUser().getStocks());
	}

	@PostMapping("/addUserStock")
	public boolean addUserStock(@RequestBody String stockIndex) {
		return accountService.addStock(stockIndex);
	}

	@PostMapping("/deleteUserStock")
	public boolean deleteUserStock(@RequestBody String stockIndex) {
		System.out.println(accountService.getLoggedUser().getStocks().size());
		return accountService.deleteStock(stockIndex);
	}

	@GetMapping("/getAllIndices")
	public List<StockIndex> getAllIndices() {
		return stockIndexService.getAllIndices();
	}

	@GetMapping("/getAllSavedStocks")
	public List<SavedStock> getAllSavedStocks() {
		return savedStockService.getAllSavedStocks(accountService.getLoggedUser().getUsername());
	}

	@PostMapping("/saveStock")
	public void saveStock(@RequestBody SavedStock savedStock) {
		savedStock.setUsername(accountService.getLoggedUser().getUsername());
		savedStockService.saveStock(savedStock);
	}

	@PostMapping("/deleteStock")
	public void deleteSavedStock(@RequestBody SavedStock stock) {
		savedStockService.deleteStock(stock);
	}

	@GetMapping("/logout")
	public void logout() {
		accountService.setLoggedUser(null);

	}
}
