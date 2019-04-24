package com.example.bridgeProject.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bridgeProject.model.Account;
import com.example.bridgeProject.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	private Account loggedUser = null;

	public void setLoggedUser(Account account) {
		loggedUser = account;
	}

	public Account getLoggedUser() {
		return loggedUser;
	}

	
	// Validates the username and password with the database
	public Account validateUser(String username, String password) {
		if (getLoggedUser() == null) {
			Account account = getAccount(username);

			if (account != null) {
				if (account.getPassword().equals(password)) {
					setLoggedUser(account);
					return account;
				} else
					return null;
			} else
				return null;
		} else
			return null;
	}

	// Returns the account from the database
	public Account getAccount(String username) {
		Optional<Account> obj = accountRepository.findById(username);
		return obj.get();
	}

	// Add an index to the user's list
	public boolean addStock(String stockIndex) {

		Account account = getAccount(loggedUser.getUsername());
		if (account.addStock(stockIndex)) {
			accountRepository.save(account);
			setLoggedUser(account);
			return true;
		} else
			return false;
	}

	// Delete an index from the user's list
	public boolean deleteStock(String stockIndex) {
		Account account = getAccount(loggedUser.getUsername());
		if (account.deleteStock(stockIndex)) {
			accountRepository.save(account);
			setLoggedUser(account);
			return true;
		} else
			return false;
	}

}
