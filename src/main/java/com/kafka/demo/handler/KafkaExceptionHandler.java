package com.kafka.demo.handler;


import com.kafka.demo.config.KafkaTopicConfig;
import com.kafka.demo.config.RetryPolicy;
import com.kafka.demo.config.TopicConfiguration;
import com.kafka.demo.exception.NonRecoverableErrException;
import com.kafka.demo.exception.PermanentErrException;
import com.kafka.demo.exception.RetriableErrException;
import com.kafka.demo.model.Header;
import com.kafka.demo.model.KafkaMessage;
import com.kafka.demo.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class KafkaExceptionHandler implements ErrorHandler {


    private final KafkaProducer kafkaProducer;

    private final KafkaTopicConfig kafkaTopicConfig;

    public KafkaExceptionHandler(KafkaProducer kafkaProducer, KafkaTopicConfig kafkaTopicConfig) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {

    }

    @Override
    public void handle(Exception ex, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {

     if(ex instanceof ListenerExecutionFailedException){
         if( ( (ListenerExecutionFailedException)ex).getRootCause() instanceof RetriableErrException) {
             handleRetriableErrException((KafkaMessage) data.value());
         }
         else if( ( (ListenerExecutionFailedException)ex).getRootCause() instanceof PermanentErrException) {
             handlePermanentErrException((KafkaMessage) data.value());
         }
         else if( ( (ListenerExecutionFailedException)ex).getRootCause() instanceof NonRecoverableErrException) {
             handleNonRecoverableErrException((KafkaMessage) data.value());
         }
         else if( ( (ListenerExecutionFailedException)ex).getRootCause() instanceof SerializationException) {
             handleSerializationExcpetion((KafkaMessage) data.value());
         }
     }

    }

    private void handleRetriableErrException(KafkaMessage kafkaMessage) {

        System.out.println("========== handlerRetriableErrException ============");
        Header header = kafkaMessage.getHeader();

        int retryCount = header.getCurrentRetryCount();
        retryCount++;

        if(!header.isRetry()) {
            Optional<String>  optionalTopicConfigName =   kafkaTopicConfig.getTopics().entrySet().stream()
                    .filter(e -> header.getNextTopicName().equals(e.getValue().getTopicName()))
                    .map(Map.Entry::getKey).findFirst();
            TopicConfiguration topicConfiguration = kafkaTopicConfig.getTopics().get(optionalTopicConfigName.get());
            header.setRetry(true);

            processRetry(header, retryCount, kafkaMessage, header.getNextTopicName(),
                    topicConfiguration.getNextTopicName());
        }else {
            Optional<String>  optionalTopicConfigName =   kafkaTopicConfig.getTopics().entrySet().stream()
                    .filter(e -> header.getTopicName().equals(e.getValue().getTopicName()))
                    .map(Map.Entry::getKey).findFirst();
            TopicConfiguration topicConfiguration = kafkaTopicConfig.getTopics().get(optionalTopicConfigName.get());

            RetryPolicy retryPolicy = topicConfiguration.getRetryPolicy();
            int maxRetries = retryPolicy.getMaxRetryCount();
            if(retryCount <= maxRetries) {
                processRetry(header, retryCount, kafkaMessage, header.getTopicName(), header.getNextTopicName());
            } else {
                if(topicConfiguration.getNextTopicName() !=null && !header.getTopicName().equals(topicConfiguration.getNextTopicName())) {
                    optionalTopicConfigName = kafkaTopicConfig.getTopics().entrySet().stream()
                            .filter(e -> header.getNextTopicName().equals(e.getValue().getNextTopicName()))
                            .map(Map.Entry::getKey).findFirst();
                    topicConfiguration = kafkaTopicConfig.getTopics().get(optionalTopicConfigName.get());
                    processRetry(header, 1, kafkaMessage, header.getNextTopicName(), topicConfiguration.getNextTopicName());
                } else {
                    System.out.println("==== Recovered message from LongRetry ==="+kafkaMessage.getPayLoad());
                    System.out.println("==== Sorry, Even the Long Retry could not help you ====");
                }
            }
        }
    }

    private void processRetry(Header header, int retryCount, KafkaMessage kafkaMessage, String currentTopic, String nextTopic){
        header.setCurrentRetryCount(retryCount);
        header.setTopicName(currentTopic);
        header.setNextTopicName(nextTopic);
        kafkaMessage.setHeader(header);
        kafkaProducer.produce(kafkaMessage);
    }

    private void handleNonRecoverableErrException(KafkaMessage kafkaMessage) {
        System.out.println("======== handleNonRecoverableErrException=============");
        Header header = kafkaMessage.getHeader();
        header.setTopicName(kafkaTopicConfig.getNonRecoverableTopicName());
        kafkaMessage.setHeader(header);
        kafkaProducer.produce(kafkaMessage);
    }

    private void handlePermanentErrException(KafkaMessage kafkaMessage) {
        System.out.println("======== handlePermanentErrException=============");
        Header header = kafkaMessage.getHeader();
        header.setTopicName(kafkaTopicConfig.getFinalErrorTopicName());
        kafkaMessage.setHeader(header);
        kafkaProducer.produce(kafkaMessage);
    }

    private void handleSerializationExcpetion(KafkaMessage kafkaMessage) {
        System.out.println("=========== handleSerializationErrException ===============");
    }

    @Override
    public void clearThreadState() {

    }

    @Override
    public boolean isAckAfterHandle() {
        return false;
    }


    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {

    }
}
