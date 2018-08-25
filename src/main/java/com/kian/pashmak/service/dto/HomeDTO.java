package com.kian.pashmak.service.dto;

import com.kian.pashmak.domain.Event;
import com.kian.pashmak.domain.enumeration.PaymentType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Debt entity.
 */
public class HomeDTO implements Serializable {

    private String cycle;
    private BalanceDTO balance;
    private List<Event> events;


    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public BalanceDTO getBalance() {
        return balance;
    }

    public void setBalance(BalanceDTO balance) {
        this.balance = balance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
