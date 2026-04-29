package com.himani.notification_system.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.himani.notification_system.metrics.MetricsService;
import com.himani.notification_system.model.Transaction;
import com.himani.notification_system.repository.TransactionRepository;

@Service
public class NotificationService {

    private final CircuitBreakerService circuitBreakerService;
    private final TransactionRepository transactionRepository;
    private final MetricsService metricsService;
    private final DeadLetterService deadLetterService;
    public NotificationService(TransactionRepository transactionRepository,MetricsService metricsService,
        DeadLetterService deadLetterService,CircuitBreakerService circuitBreakerService) {
        this.transactionRepository = transactionRepository;
        this.metricsService = metricsService;
        this.deadLetterService = deadLetterService;
        this.circuitBreakerService = circuitBreakerService;
    }

    @Async("notificationExecutor")
    public void processTransactionAsync(Transaction transaction) {

        if (!circuitBreakerService.allowRequest()) {
            System.out.println("Circuit breaker OPEN — rejecting request");

            transaction.setStatus("REJECTED");
            transactionRepository.save(transaction);

            metricsService.incrementRejected();
            return;
        }

        System.out.println("Processing on thread: " + Thread.currentThread().getName());

        int retry = 3;

        while (retry > 0) {
            try {

                simulateNotification();

                transaction.setStatus("SUCCESS");
                transactionRepository.save(transaction);

                metricsService.incrementSuccess();
                circuitBreakerService.recordSuccess();

                System.out.println("TransactionId: " + transaction.getId() +
                    " | Status: SUCCESS");

                return;

            } catch (Exception e) {
                retry--;
                System.out.println("Retrying... attempts left: " + retry);
            }
        }

        // Only once after all retries fail
        circuitBreakerService.recordFailure();

        transaction.setStatus("FAILED");
        transactionRepository.save(transaction);

        metricsService.incrementFailure();

        deadLetterService.sendToDLQ(transaction);

        System.out.println("TransactionId: " + transaction.getId() +
            " | Status: FAILED (sent to DLQ)");
    }

    private void simulateNotification() {

        try {

            Thread.sleep(200);

            if (Math.random() < 0.3) {
                throw new RuntimeException("Notification Failed");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
}