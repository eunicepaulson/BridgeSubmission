package com.example.bridgeProject.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bridgeProject.model.StockIndex;

@Repository
public interface StockIndexRepository extends MongoRepository<StockIndex, String> {
	
	public List<StockIndex> findAll();
}
