package com.kian.pashmak.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Checkin.
 */
@Entity
@Table(name = "checkin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Checkin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "checkin_time")
    private ZonedDateTime checkinTime;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCheckinTime() {
        return checkinTime;
    }

    public Checkin checkinTime(ZonedDateTime checkinTime) {
        this.checkinTime = checkinTime;
        return this;
    }

    public void setCheckinTime(ZonedDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getMessage() {
        return message;
    }

    public Checkin message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public Checkin user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Checkin checkin = (Checkin) o;
        if (checkin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Checkin{" +
            "id=" + getId() +
            ", checkinTime='" + getCheckinTime() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
