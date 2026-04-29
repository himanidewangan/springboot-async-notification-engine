package com.himani.notification_system.queue;

import com.himani.notification_system.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class NotificationQueue {

    private final BlockingQueue<Transaction> queue = new LinkedBlockingQueue<>();

    public void add(Transaction transaction) {
        queue.add(transaction);
    }

    public Transaction take() throws InterruptedException {
        return queue.take();
    }
}