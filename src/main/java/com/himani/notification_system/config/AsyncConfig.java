package com.himani.notification_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "notificationExecutor")
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);     
        executor.setMaxPoolSize(10);     
        executor.setQueueCapacity(50);   
        executor.setThreadNamePrefix("Notification-");

        executor.setRejectedExecutionHandler((r, executor1) -> {
            System.out.println("System overloaded. Request rejected.");
        });
        executor.initialize();

        return executor;
    }
}