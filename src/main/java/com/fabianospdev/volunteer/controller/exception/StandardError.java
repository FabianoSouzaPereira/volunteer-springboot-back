package com.fabianospdev.volunteer.controller.exception;

import java.io.Serializable;

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError() {
    }

    public StandardError(Long timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static final class Builder {
        private final StandardError error = new StandardError();

        public Builder timestamp(Long timestamp) {
            error.setTimestamp(timestamp);
            return this;
        }

        public Builder status(Integer status) {
            error.setStatus(status);
            return this;
        }

        public Builder error(String errorName) {
            error.setError(errorName);
            return this;
        }

        public Builder message(String message) {
            error.setMessage(message);
            return this;
        }

        public Builder path(String path) {
            error.setPath(path);
            return this;
        }

        public StandardError build() {
            return error;
        }
    }
}
