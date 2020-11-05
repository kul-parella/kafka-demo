package com.kafka.demo.listener;

import com.kafka.demo.model.KafkaMessage;
import com.kafka.demo.handler.IKafkaMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaerrorMessageListener {

    @Autowired
    private IKafkaMessageHandler kafkaMessageHandler;

    @KafkaListener(topics ="#{'${spring.demo.kafka.nonRecoverableTopicName}'}",
            containerFactory = "kafkaListenerContainerFactory")
    public void processNonRecoverable(KafkaMessage kafkaMessage) {
        System.out.println("==== From f/w processNonRecoverable ==== message: " +kafkaMessage.getPayLoad());
    }

    @KafkaListener(topics ="#{'${spring.demo.kafka.finalErrorTopicName}'}",
            containerFactory = "kafkaListenerContainerFactory")
    public void processFinalError(KafkaMessage kafkaMessage) {
        System.out.println("==== From f/w processFinalError ==== message: " +kafkaMessage.getPayLoad());
    }

}
