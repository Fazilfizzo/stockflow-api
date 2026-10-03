package com.fizoind.stockflow_api.interceptor;

import com.fizoind.stockflow_api.pricing.PricingPlanService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


public class RateLimitInterceptor implements HandlerInterceptor {

    private final PricingPlanService pricingPlanService;

    public RateLimitInterceptor(PricingPlanService pricingPlanService) {
        this.pricingPlanService = pricingPlanService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        // Allow CORS preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || apiKey.isBlank()) {
            response.sendError(
                    HttpStatus.BAD_REQUEST.value(),
                    "Missing header: X-API-KEY"
            );
            return false;
        }

        Bucket tokenBucket = pricingPlanService.resolveBucket(apiKey);

        ConsumptionProbe probe =
                tokenBucket.tryConsumeAndReturnRemaining(1);

        System.out.println("Consumed? " + probe.isConsumed());

        if (probe.isConsumed()) {

            response.addHeader(
                    "X-Rate-Limiting",
                    String.valueOf(probe.getRemainingTokens())
            );

            return true;

        } else {

            long waitForRefill =
                    probe.getNanosToWaitForRefill() / 1_000_000_000;

            response.addHeader(
                    "X-Rate-Limiting",
                    String.valueOf(waitForRefill)
            );

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");

            response.getWriter().write("""
        {
            "error": "RATE_LIMIT_EXCEEDED",
            "message": "Too many requests. Please try again later."
        }
        """);

            return false;
        }
    }
}
