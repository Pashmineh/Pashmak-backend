package com.kian.pashmak.service.dto;

import java.io.Serializable;

/**
 * A DTO for the Event entity.
 */
public class VoteDTO implements Serializable {

    private Long poll;
    private Long item;

    public Long getPoll() {
        return poll;
    }

    public void setPoll(Long poll) {
        this.poll = poll;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "VoteDTO{" +
            "poll=" + poll +
            ", item=" + item +
            '}';
    }
}
