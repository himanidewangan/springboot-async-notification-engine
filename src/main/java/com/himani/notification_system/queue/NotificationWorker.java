package com.himani.notification_system.queue;

import com.himani.notification_system.model.Transaction;
import com.himani.notification_system.service.NotificationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class NotificationWorker {

    private final NotificationQueue queue;
    private final NotificationService service;

    public NotificationWorker(NotificationQueue queue,
                              NotificationService service) {
        this.queue = queue;
        this.service = service;
    }

    @PostConstruct
    public void startWorker() {

        for (int i = 0; i < 3; i++) {

            new Thread(() -> {

                while (true) {

                    try {

                        Transaction transaction = queue.take();

                        service.processTransactionAsync(transaction);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }).start();
        }
    }
}