package com.example.bridgeProject.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bridgeProject.model.SavedStock;

@Repository
public interface SavedStockRepository extends MongoRepository<SavedStock,String> {		
	
	public List<SavedStock> findAllByUsernameOrderByDateSavedDesc(String username);
}
