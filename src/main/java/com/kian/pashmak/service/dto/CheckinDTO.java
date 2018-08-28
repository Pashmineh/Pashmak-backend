package com.kian.pashmak.service.dto;

import com.kian.pashmak.web.rest.vm.CheckinType;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Checkin entity.
 */
public class CheckinDTO implements Serializable {

    private Long id;

    private ZonedDateTime checkinTime;
    private Long checkinTimeEpoch;

    private String message;

    private Long userId;
    private CheckinType checkinType;
    private String userLogin;


    public CheckinType getCheckinType() {
        return checkinType;
    }

    public void setCheckinType(CheckinType checkinType) {
        this.checkinType = checkinType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(ZonedDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getCheckinTimeEpoch() {
        return checkinTime.toEpochSecond()*1000;
    }

    public void setCheckinTimeEpoch(Long checkinTimeEpoch) {
        this.checkinTimeEpoch = checkinTimeEpoch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckinDTO checkinDTO = (CheckinDTO) o;
        if (checkinDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckinDTO{" +
            "id=" + getId() +
            ", checkinTime='" + getCheckinTime() + "'" +
            ", message='" + getMessage() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
