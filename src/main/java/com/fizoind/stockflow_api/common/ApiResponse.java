package com.fizoind.stockflow_api.common;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T>{
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private int errorCode;
    private long timeStamp;
    private String path;
    private LocalDateTime time;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, List<String> errors, int errorCode, long timeStamp, String path, LocalDateTime time) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
        this.path = path;
        this.time = time;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

