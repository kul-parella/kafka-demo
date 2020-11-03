package com.kafka.example.kafka_demo.com.kafka.example.consumers;

import com.kafka.example.kafka_demo.com.kafka.example.exception.SystemException;
import com.kafka.example.kafka_demo.com.kafka.example.producer.KafkaProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.kafka.example.kafka_demo.com.kafka.example.util.KafkaConstants.*;

@Service
public class KafkaDelayConsumer1 {

    private KafkaProducer kafkaProducer;

    @KafkaListener(topics= POC_TOPIC+DELAY1, containerFactory = "kafkaDelay1ListenerContainerFactory", groupId= POC_APPLICATION_GROUP)
    public void consume(String message) {
        System.out.println("=======KafkaDelayConsumer1=============consumed message"+ message);

    if(message.contains("exception")) {
        //throw new SystemException("Failed message "+ message);
        System.out.println("==========Error Occured in Kafka consumer 1 ==================");
        kafkaProducer.produce(POC_TOPIC+DELAY2, message);
    }
    }

}
