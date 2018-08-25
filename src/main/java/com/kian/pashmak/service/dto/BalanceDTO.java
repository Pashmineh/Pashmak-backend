package com.kian.pashmak.service.dto;

/**
 * Created by arsham on 8/25/18.
 */
import java.math.BigDecimal;

public class BalanceDTO {
    private BigDecimal balance;
    private BigDecimal totalPaid;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }
}
