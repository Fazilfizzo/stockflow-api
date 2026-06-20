package com.fizoind.stockflow_api.configuration;

import com.fizoind.stockflow_api.interceptor.RateLimitInterceptor;
import com.fizoind.stockflow_api.pricing.PricingPlanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final PricingPlanService pricingPlanService;

    public InterceptorConfig(PricingPlanService pricingPlanService) {
        this.pricingPlanService = pricingPlanService;
    }

    @Bean
    public RateLimitInterceptor rateLimitInterceptor() {
        return new RateLimitInterceptor(pricingPlanService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(rateLimitInterceptor())
                .addPathPatterns("/auth/login");
    }
}
