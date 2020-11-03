package com.kafka.example.kafka_demo.com.kafka.example.consumers;

import com.kafka.example.kafka_demo.com.kafka.example.exception.SystemException;
import com.kafka.example.kafka_demo.com.kafka.example.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.kafka.example.kafka_demo.com.kafka.example.util.KafkaConstants.*;

@Service
public class KafkaConsumer {


    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics= POC_TOPIC, containerFactory = "kafkaListenerContainerFactory", groupId = POC_APPLICATION_GROUP)
    public void consume(String message) {
        System.out.println("=========Kafka main Consumer==========Consumed message"+message);

        try {
            if(message.contains("exception")){
                throw new SystemException("Failed message: "+ message);
            }
        }catch (SystemException sysEx) {
            System.out.println("==========Error Occured in Kafka main Consumer============");
            kafkaProducer.produce(POC_TOPIC+DELAY1, message);
        }
    }

}
