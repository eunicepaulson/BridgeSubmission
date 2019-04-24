package com.example.bridgeProject.service;

import org.springframework.stereotype.Service;
import com.example.bridgeProject.model.SavedStock;
import com.example.bridgeProject.repository.SavedStockRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SavedStockService {

	@Autowired
	private SavedStockRepository savedStockRepository;

	//display all the stocks saved by the user in descending order of dates saved
	public List<SavedStock> getAllSavedStocks(String username) {		
		return savedStockRepository.findAllByUsernameOrderByDateSavedDesc(username);
	}

	//Save a particular stock to the database
	public void saveStock(SavedStock stock) {
		stock.setDateSaved(LocalDate.now().toString());
		 savedStockRepository.save(stock);
	}
	
	//Delete a stock from the database
	public void deleteStock(SavedStock stock)
	{
		savedStockRepository.deleteById(stock.getId());;
	}

}
