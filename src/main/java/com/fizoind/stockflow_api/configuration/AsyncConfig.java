package com.fizoind.stockflow_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("email-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();

        return executor;
    }

    @Bean(name = "invoiceExecutor")
    public Executor invoiceExecutor() {
        ThreadPoolTaskExecutor invoiceExecutor = new ThreadPoolTaskExecutor();

        invoiceExecutor.setCorePoolSize(4);
        invoiceExecutor.setMaxPoolSize(8);
        invoiceExecutor.setQueueCapacity(100);
        invoiceExecutor.setThreadNamePrefix("invoice-thread-");

        invoiceExecutor.setWaitForTasksToCompleteOnShutdown(true);
        invoiceExecutor.setAwaitTerminationSeconds(30);
        invoiceExecutor.initialize();

        return invoiceExecutor;
    }
}
