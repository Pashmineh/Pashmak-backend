
package com.kian.pashmak.service.dto.push;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tokens",
    "platform",
    "title",
    "priority",
    "topic",
    "mutable_content",
    "alert"
})
public class Notification {

    @JsonProperty("tokens")
    private List<String> tokens = null;
    @JsonProperty("platform")
    private Integer platform;
    @JsonProperty("title")
    private String title;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("topic")
    private String topic;
    @JsonProperty("mutable_content")
    private Boolean mutableContent;
    @JsonProperty("alert")
    private Alert alert;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tokens")
    public List<String> getTokens() {
        return tokens;
    }

    @JsonProperty("tokens")
    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    @JsonProperty("platform")
    public Integer getPlatform() {
        return platform;
    }

    @JsonProperty("platform")
    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("topic")
    public String getTopic() {
        return topic;
    }

    @JsonProperty("topic")
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @JsonProperty("mutable_content")
    public Boolean getMutableContent() {
        return mutableContent;
    }

    @JsonProperty("mutable_content")
    public void setMutableContent(Boolean mutableContent) {
        this.mutableContent = mutableContent;
    }

    @JsonProperty("alert")
    public Alert getAlert() {
        return alert;
    }

    @JsonProperty("alert")
    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
