package com.kafka.demo.handler;

import com.kafka.demo.exception.NonRecoverableErrException;
import com.kafka.demo.exception.PermanentErrException;
import com.kafka.demo.model.KafkaMessage;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.kafka.common.errors.SerializationException;


public interface IKafkaMessageHandler {

    void process(KafkaMessage kafkaMessage) throws RetriableException, NonRecoverableErrException,
            PermanentErrException, SerializationException;



}
