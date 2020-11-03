package com.kafka.example.kafka_demo.com.kafka.example.consumers;

import com.kafka.example.kafka_demo.com.kafka.example.exception.SystemException;
import com.kafka.example.kafka_demo.com.kafka.example.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.kafka.example.kafka_demo.com.kafka.example.util.KafkaConstants.*;

@Service
public class KafkaDelayConsumer2 {

    private KafkaProducer kafkaProducer;

    @KafkaListener(topics= POC_TOPIC+DELAY2, containerFactory = "kafkaDelay2ListenerContainerFactory", groupId= POC_APPLICATION_GROUP)
    public void consume(String message) {
        System.out.println("=======KafkaDelayConsumer2=============consumed message"+ message);

        if(message.contains("exception")) {
            System.out.println("==========Error Occured in KafkaConsumer 2 ==================");
        }


    }


}
