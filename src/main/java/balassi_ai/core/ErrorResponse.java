package balassi_ai.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    private String message;

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
