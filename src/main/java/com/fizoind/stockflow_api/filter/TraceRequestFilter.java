package com.fizoind.stockflow_api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceRequestFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String correlationId = UUID.randomUUID().toString();

        MDC.put(CORRELATION_ID, correlationId);

        httpServletResponse.setHeader("X-Correlation-Id", correlationId);

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            MDC.clear();
        }
    }
}
