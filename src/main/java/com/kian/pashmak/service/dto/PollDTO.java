package com.kian.pashmak.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the Event entity.
 */
public class PollDTO  implements Serializable {
    private Long id;

    private String question;


    private Boolean anonymous;

    private String imgsrc;

    private Long answerLimit;

    private Long totalVote=0l;
    List<PollItemDTO> itemDTOS = new ArrayList<>();

    public List<PollItemDTO> getItemDTOS() {
        return itemDTOS;
    }

    public void setItemDTOS(List<PollItemDTO> itemDTOS) {
        this.itemDTOS = itemDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public Long getAnswerLimit() {
        return answerLimit;
    }

    public void setAnswerLimit(Long answerLimit) {
        this.answerLimit = answerLimit;
    }

    public Long getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Long totalVote) {
        this.totalVote = totalVote;
    }
}
