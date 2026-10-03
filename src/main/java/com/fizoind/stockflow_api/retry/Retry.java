package com.fizoind.stockflow_api.retry;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class Retry {

    private boolean isRetryable(Exception e) {
        return e instanceof SocketTimeoutException || e instanceof ConnectException || e instanceof HttpServerErrorException || e instanceof ResourceAccessException;
    }
}
