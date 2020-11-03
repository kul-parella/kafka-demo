package com.kafka.example.kafka_demo.com.kafka.example.controller;


import com.kafka.example.kafka_demo.com.kafka.example.producer.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.kafka.example.kafka_demo.com.kafka.example.util.KafkaConstants.POC_TOPIC;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;


    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/createMessage")
    public ResponseEntity<String> createMessage(@RequestParam("message") String message) {
        kafkaProducer.produce(POC_TOPIC, message);
        return ResponseEntity.ok("Success");
    }


}
