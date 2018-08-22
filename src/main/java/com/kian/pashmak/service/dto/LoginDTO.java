package com.kian.pashmak.service.dto;

import com.kian.pashmak.domain.Event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * A DTO for the Debt entity.
 */
public class LoginDTO implements Serializable {

    private String name;
    private String lastName;
    private String token;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
