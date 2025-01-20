package com.Fintech.demo.controller;

import com.Fintech.demo.entity.Account;
import com.Fintech.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // 계좌 생성
    @PostMapping("/create")
    public Account createAccount(@RequestParam Long userId) {
        return accountService.createAccount(userId);
    }

    // 특정 계좌 조회
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    // 사용자의 모든 계좌 조회
    @GetMapping("/user/{userId}")
    public List<Account> getUserAccounts(@PathVariable Long userId) {
        return accountService.getUserAccounts(userId);
    }

    // 입금
    @PostMapping("/deposit")
    public Account deposit(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        return accountService.deposit(accountId, amount);
    }

    // 출금
    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        return accountService.withdraw(accountId, amount);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam BigDecimal amount) {
        accountService.transfer(fromAccountId, toAccountId, amount);
        return "Transfer successful!";
    }

}
