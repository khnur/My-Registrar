package com.example.myregistrar.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "registrar")
    public void listenMessage(String data) {
        System.out.println("Listener received: " + data);
    }
}
