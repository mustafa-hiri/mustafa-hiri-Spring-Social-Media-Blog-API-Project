package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * This method handles user registration.
     * Returns:
     * - saved Account if registration is successful
     * - Integer 400 if input is invalid
     * - Integer 409 if username already exists
     */
    public Object register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return 400; // Bad Request: missing or blank username
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return 400; // Bad Request: password too short
        }

        if (accountRepository.existsByUsername(account.getUsername())) {
            return 409; // Conflict: username already exists
        }

        Account savedAccount = accountRepository.save(account); // Save the new user
        return savedAccount;
    }

    /**
     * This method handles user login.
     * Returns:
     * - the matching Account if found
     * - null if login fails (unauthorized)
     */
    public Account login(Account account) {
        Optional<Account> existenceAccount = accountRepository.findByUsernameAndPassword(
            account.getUsername(), account.getPassword()
        );

        if (existenceAccount.isEmpty()) {
            return null; // Unauthorized: login failed
        }

        return existenceAccount.get(); // Login successful: return account
    }
}