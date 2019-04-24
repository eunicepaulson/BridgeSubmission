package com.example.bridgeProject.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bridgeProject.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
	
	public Optional<Account> findById(String username);

}
