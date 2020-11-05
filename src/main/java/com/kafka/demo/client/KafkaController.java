package com.kafka.demo.client;


import com.kafka.demo.model.Header;
import com.kafka.demo.model.KafkaMessage;
import com.kafka.demo.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/createMessage")
    public ResponseEntity<String> createMessage(@RequestParam("message") String payLoad) {

        Header header = new Header(false, 0, "kafka-sample",
                "kafka-sample_delay1");
        KafkaMessage kafkaMessage = new KafkaMessage(Long.valueOf(1), "originalEvent", new Date(),
                payLoad, header);
        kafkaProducer.produce(kafkaMessage);
        return ResponseEntity.ok("successfully produced the message to Original topic !");
    }


}
