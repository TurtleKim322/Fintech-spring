package com.Fintech.demo.service;

import com.Fintech.demo.entity.Account;
import com.Fintech.demo.entity.Transaction;
import com.Fintech.demo.entity.TransactionType;
import com.Fintech.demo.repository.AccountRepository;
import com.Fintech.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    //거래 내역 저장
    @Transactional
    public Transaction saveTransaction(Long accountId, TransactionType type, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Transaction 생성 시, 올바른 TransactionType 사용
        Transaction transaction = new Transaction(account, type, amount, description);
        return transactionRepository.save(transaction);
    }

    // 특정 계좌의 거래 내역 조회
    public List<Transaction> getTransactionsByAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return transactionRepository.findByAccount(account);
    }
}
