package com.himani.notification_system.controller;

import com.himani.notification_system.kafka.KafkaProducerService;
import com.himani.notification_system.model.Transaction;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final KafkaProducerService kafkaProducerService;

    public TransactionController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
        kafkaProducerService.sendTransaction(transaction);
        return ResponseEntity.ok("Transaction submitted for processing");
    }
}
