package com.Fintech.demo.repository;

import com.Fintech.demo.entity.Account;
import com.Fintech.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByUser(User user);
}
