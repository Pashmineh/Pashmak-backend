package com.kian.pashmak.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.kian.pashmak.domain.enumeration.PaymentType;

/**
 * A Debt.
 */
@Entity
@Table(name = "debt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Debt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_time")
    private ZonedDateTime paymentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private PaymentType reason;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Debt amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getPaymentTime() {
        return paymentTime;
    }

    public Debt paymentTime(ZonedDateTime paymentTime) {
        this.paymentTime = paymentTime;
        return this;
    }

    public void setPaymentTime(ZonedDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public PaymentType getReason() {
        return reason;
    }

    public Debt reason(PaymentType reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(PaymentType reason) {
        this.reason = reason;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Debt debt = (Debt) o;
        if (debt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), debt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Debt{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", paymentTime='" + getPaymentTime() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
