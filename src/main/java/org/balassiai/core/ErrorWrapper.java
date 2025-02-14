package org.balassiai.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorWrapper {
    @JsonProperty("error")
    private ErrorResponse error;

    public ErrorWrapper(ErrorResponse error) {
        this.error = error;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}
