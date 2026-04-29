package com.himani.notification_system.metrics;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private AtomicInteger success = new AtomicInteger(0);
    private AtomicInteger failure = new AtomicInteger(0);
    private AtomicInteger rejected = new AtomicInteger(0);

    public void incrementSuccess() {
        success.incrementAndGet();
    }

    public void incrementFailure() {
        failure.incrementAndGet();
    }

    public void incrementRejected() {
        rejected.incrementAndGet();
    }

    public int getSuccess() {
        return success.get();
    }

    public int getFailure() {
        return failure.get();
    }

    public int getRejected() {
        return rejected.get();
    }
}