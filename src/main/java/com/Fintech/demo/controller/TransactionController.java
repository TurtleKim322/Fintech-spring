package com.Fintech.demo.controller;

import com.Fintech.demo.entity.Transaction;
import com.Fintech.demo.entity.TransactionType;
import com.Fintech.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 거래 내역 저장
    @PostMapping("/save")
    public Transaction saveTransaction(@RequestParam Long accountId,
                                       @RequestParam TransactionType type,
                                       @RequestParam BigDecimal amount,
                                       @RequestParam(required = false) String description) {
        return transactionService.saveTransaction(accountId, type, amount, description);
    }

    // 특정 계좌의 거래 내역 조회
    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsByAccount(@PathVariable Long accountId) {
        return transactionService.getTransactionsByAccount(accountId);
    }
}
