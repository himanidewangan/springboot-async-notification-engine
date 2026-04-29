package com.himani.notification_system.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.himani.notification_system.model.Transaction;
import com.himani.notification_system.service.NotificationService;

@Service
public class KafkaConsumerService {

    private final NotificationService notificationService;

    public KafkaConsumerService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "transactions-topic", groupId = "notification-group")
    public void consume(Transaction transaction) {
        notificationService.processTransactionAsync(transaction);
    }
} 