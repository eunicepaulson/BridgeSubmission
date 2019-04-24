package com.example.bridgeProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.cdi.MongoRepositoryBean;
import org.springframework.stereotype.Service;
import com.example.bridgeProject.model.StockIndex;
import com.example.bridgeProject.repository.StockIndexRepository;

@Service
public class StockIndexService  {

	@Autowired
	StockIndexRepository stockIndexRepository;
	
	//return the index and the name of the companies used
	public List<StockIndex> getAllIndices()
	{
		return stockIndexRepository.findAll();
	}
	
	
}
