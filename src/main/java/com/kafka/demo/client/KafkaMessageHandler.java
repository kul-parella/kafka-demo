package com.kafka.demo.client;

import com.kafka.demo.exception.NonRecoverableErrException;
import com.kafka.demo.exception.PermanentErrException;
import com.kafka.demo.exception.RetriableErrException;
import com.kafka.demo.handler.IKafkaMessageHandler;
import com.kafka.demo.model.Header;
import com.kafka.demo.model.KafkaMessage;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageHandler implements IKafkaMessageHandler {

    @Override
    public void process(KafkaMessage kafkaMessage) throws RetriableErrException, NonRecoverableErrException,
            PermanentErrException, SerializationException {

        String payLoad = kafkaMessage.getPayLoad();
        Header header  = kafkaMessage.getHeader();

        System.out.println("=========From Client: KafkaMessageHandler================"+payLoad);
        System.out.println("=========From Client: getCurrentRetryCount==============="+header.getCurrentRetryCount());

        if(payLoad.contains("Retriable")) {
            throw new RetriableErrException();
        }
        else if(payLoad.contains("NonRecoverable")) {
            throw new NonRecoverableErrException();
        }
        else if(payLoad.contains("Permanent")) {
            throw new PermanentErrException();
        }
        else if(payLoad.contains("Serialization")) {
            throw new SerializationException();
        }



    }
}
