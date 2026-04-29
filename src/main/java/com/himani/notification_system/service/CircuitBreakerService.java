package com.himani.notification_system.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CircuitBreakerService {

    private AtomicInteger failureCount = new AtomicInteger(0);

    private volatile boolean open = false;

    private long lastFailureTime = 0;

    private static final int FAILURE_THRESHOLD = 5;
    private static final long RESET_TIMEOUT = 5000;

    public boolean allowRequest() {

        if (!open) {
            return true;
        }

        long now = System.currentTimeMillis();

        if (now - lastFailureTime > RESET_TIMEOUT) {
            open = false;
            failureCount.set(0);
            System.out.println("Circuit Breaker HALF-OPEN");
            return true;
        }

        return false;
    }

    public void recordSuccess() {
        failureCount.set(0);
    }

    public void recordFailure() {

        int failures = failureCount.incrementAndGet();

        if (failures >= FAILURE_THRESHOLD) {
            open = true;
            lastFailureTime = System.currentTimeMillis();

            System.out.println("Circuit Breaker OPEN");
        }
    }
}