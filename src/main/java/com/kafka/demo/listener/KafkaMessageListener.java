package com.kafka.demo.listener;

import com.kafka.demo.model.KafkaMessage;
import com.kafka.demo.handler.IKafkaMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    @Autowired
    private IKafkaMessageHandler kafkaMessageHandler;

    @KafkaListener(autoStartup = "#{'${spring.demo.kafka.enableListeners}'}",
            topics = "#{'${spring.demo.kafka.topics.originalTopic.topicName}'}",
            containerFactory = "kafkaListenerContainerFactory")
    public void onRecieve(KafkaMessage kafkaMessage) {
        System.out.println("==== From f/w KafkaMessageListener ==== Recieved message: " +kafkaMessage.getPayLoad());
        kafkaMessageHandler.process(kafkaMessage);
    }


}
