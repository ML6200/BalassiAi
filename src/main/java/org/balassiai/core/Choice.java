package org.balassiai.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {
    private Message message;

    @JsonProperty("message")
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
