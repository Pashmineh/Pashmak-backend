package com.kian.pashmak.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * A Event.
 */
@Entity
@Table(name = "poll")
public class Poll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "question")
    private String question;


    private Boolean anonymous;

    private String imgsrc;

    private Long answerLimit;

    private Long totalVote=0l;

    @OneToMany
    private Set<PollItem> pollItemSet;

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

    public Long getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Long totalVote) {
        this.totalVote = totalVote;
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

    public Long getAnswerLimit() {
        return answerLimit;
    }

    public void setAnswerLimit(Long answerLimit) {
        this.answerLimit = answerLimit;
    }

    public Set<PollItem> getPollItemSet() {
        return pollItemSet;
    }

    public void setPollItemSet(Set<PollItem> pollItemSet) {
        this.pollItemSet = pollItemSet;
    }
}
