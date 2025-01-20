package com.Fintech.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String accountNumber; // 계좌번호

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO; // 잔액

    public Account(User user, String accountNumber) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
    }
}
