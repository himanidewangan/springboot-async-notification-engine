package com.himani.notification_system.controller;

import com.himani.notification_system.service.DeadLetterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DLQController {

    private final DeadLetterService deadLetterService;

    public DLQController(DeadLetterService deadLetterService) {
        this.deadLetterService = deadLetterService;
    }

    @GetMapping("/dlq")
    public int getDLQSize() {
        return deadLetterService.getDLQSize();
    }
}