package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import java.util.Optional;

@Service
public class AccountService {

    // This allows us to access and use the AccountRepository methods
    @Autowired
    private AccountRepository accountRepository;

    /**
     * This method handles user registration.
     * It checks that the username is not blank, the password is at least 4 characters long,
     * and that the username isn't already taken. If everything is good, it saves the account.
     * Otherwise, it returns the appropriate HTTP error status.
     */
    public ResponseEntity<?> register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return ResponseEntity.status(400).build(); // Bad Request: missing or blank username
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build(); // Bad Request: password too short
        }
        if (accountRepository.existsByUsername(account.getUsername())) {
            return ResponseEntity.status(409).build(); // Conflict: username already exists
        }

        Account savedAccount = accountRepository.save(account); // Save the new user
        return ResponseEntity.ok(savedAccount); // Return the saved account (with ID)
    }

    /**
     * This method handles user login.
     * It tries to find an account with the given username and password.
     * If found, login is successful. If not, it returns a 401 Unauthorized.
     */
    public ResponseEntity<?> login(Account account){
        Optional<Account> existenceAccount = accountRepository.findByUsernameAndPassword(
            account.getUsername(), account.getPassword()
        );

        if (existenceAccount.isEmpty()) {
            return ResponseEntity.status(401).build(); // Unauthorized: login failed
        }

        return ResponseEntity.ok(existenceAccount); // Login successful: return account
    }

}