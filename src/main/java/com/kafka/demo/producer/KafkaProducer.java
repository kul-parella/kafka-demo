package com.kafka.demo.producer;

import com.kafka.demo.model.Header;
import com.kafka.demo.model.KafkaMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {

    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce( KafkaMessage kafkaMessage) {
        Header header = kafkaMessage.getHeader();
        System.out.println("=========From F/W: Producing message: " + kafkaMessage.getPayLoad() + "==================");
        kafkaTemplate.send(header.getTopicName(), kafkaMessage);
    }

}
