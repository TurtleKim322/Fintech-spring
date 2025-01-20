package com.Fintech.demo.service;

import com.Fintech.demo.entity.Account;
import com.Fintech.demo.entity.TransactionType;
import com.Fintech.demo.entity.User;
import com.Fintech.demo.repository.AccountRepository;
import com.Fintech.demo.repository.UserRepository;
import com.Fintech.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;  // TransactionService 추가

    //  계좌 생성
    public Account createAccount(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        String accountNumber = generateAccountNumber();

        Account account = new Account(user, accountNumber);
        return accountRepository.save(account);
    }

    // 특정 계좌 조회
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    //  특정 사용자의 모든 계좌 조회
    public List<Account> getUserAccounts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepository.findByUser(user);
    }

    //  입금 기능
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance().add(amount));

        //  거래 내역 저장
        transactionService.saveTransaction(accountId, TransactionType.DEPOSIT, amount, "Deposit");

        return accountRepository.save(account);
    }

    //  출금 기능
    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));

        // 거래 내역 저장
        transactionService.saveTransaction(accountId, TransactionType.WITHDRAW, amount, "Withdraw");

        return accountRepository.save(account);
    }

    //계좌 간 송금 기능
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // 출금 (보내는 계좌)
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        transactionService.saveTransaction(fromAccountId, TransactionType.TRANSFER, amount, "Transferred to " + toAccount.getAccountNumber());

        // 입금 (받는 계좌)
        toAccount.setBalance(toAccount.getBalance().add(amount));
        transactionService.saveTransaction(toAccountId, TransactionType.TRANSFER, amount, "Received from " + fromAccount.getAccountNumber());

        // 계좌 정보 저장
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    // 랜덤 계좌번호 생성
    private String generateAccountNumber() {
        Random random = new Random();
        return "ACCT-" + (100000 + random.nextInt(900000));
    }
}
