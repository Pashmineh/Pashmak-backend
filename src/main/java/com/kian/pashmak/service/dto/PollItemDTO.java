package com.kian.pashmak.service.dto;

import com.kian.pashmak.domain.Poll;
import com.kian.pashmak.domain.PollItem;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Event entity.
 */
public class PollItemDTO  implements Serializable {
    private Long id;

    private String title;

    private String imgsrc;

    private Long number=0l;



    private Boolean voted;

    public Boolean getVoted() {
        return voted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setVoted(Boolean voted) {
        this.voted = voted;


    }


}
