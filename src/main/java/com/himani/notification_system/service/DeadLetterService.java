package com.himani.notification_system.service;

import org.springframework.stereotype.Service;

import com.himani.notification_system.model.Transaction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class DeadLetterService {

    private final BlockingQueue<Transaction> deadLetterQueue =
            new LinkedBlockingQueue<>();

    public void sendToDLQ(Transaction transaction) {
        deadLetterQueue.offer(transaction);
        System.out.println("Sent to Dead Letter Queue for user: " + transaction.getUserId());
    }

    public int getDLQSize() {
        return deadLetterQueue.size();
    }
}