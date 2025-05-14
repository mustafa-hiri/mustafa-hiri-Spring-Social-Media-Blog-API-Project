package com.example.repository;

import com.example.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface lets us talk to the database about Account objects.
 * We don't have to write SQL — Spring figures it out based on the method names.
 * By extending JpaRepository, we get common database methods like save, findAll, deleteById, etc.
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Checks if an account with the given username already exists in the database
    boolean existsByUsername(String username);

    // Retrieves an account with the given username and password (used during login)
    Optional<Account> findByUsernameAndPassword(String username, String password);
}