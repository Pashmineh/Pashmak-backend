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

    private BigDecimal balance;
    private List<Event> events;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
