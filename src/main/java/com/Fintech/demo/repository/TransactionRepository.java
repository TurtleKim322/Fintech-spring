package com.Fintech.demo.repository;

import com.Fintech.demo.entity.Account;
import com.Fintech.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}
