package com.kafka.demo.listener;

import com.kafka.demo.config.KafkaTopicConfig;
import com.kafka.demo.config.RetryPolicy;
import com.kafka.demo.config.TopicConfiguration;
import com.kafka.demo.model.Header;
import com.kafka.demo.model.KafkaMessage;
import com.kafka.demo.producer.KafkaProducer;
import com.kafka.demo.handler.IKafkaMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaRetryMessageListener {

    @Autowired
    private IKafkaMessageHandler kafkaMessageHandler;

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(autoStartup = "#{'${spring.demo.kafka.enableListeners}'}",
            topics = "#{'${spring.demo.kafka.topics.delayTopic1.topicName}'}",
            containerFactory = "kafkaDelay1ListenerContainerFactory")
    public void shortRetry(KafkaMessage kafkaMessage) throws Exception {

        System.out.println("==== In ShortRetry ==== Recieved message: " +kafkaMessage.getPayLoad());
        Header header = kafkaMessage.getHeader();
        TopicConfiguration topicConfiguration = getTopicConfiguration(header.getTopicName());
        RetryPolicy retryPolicy = topicConfiguration.getRetryPolicy();
        TimeUnit.SECONDS.sleep(retryPolicy.getRetryDelay());
        kafkaMessageHandler.process(kafkaMessage);

    }

    @KafkaListener(autoStartup = "#{'${spring.demo.kafka.enableListeners}'}",
            topics = "#{'${spring.demo.kafka.topics.delayTopic2.topicName}'}",
            containerFactory = "kafkaDelay2ListenerContainerFactory")
    public void longRetry(KafkaMessage kafkaMessage) throws Exception {

        System.out.println("==== In LongRetry ==== Recieved message: " +kafkaMessage.getPayLoad());
        Header header = kafkaMessage.getHeader();
        TopicConfiguration  topicConfiguration = getTopicConfiguration(header.getTopicName());
        RetryPolicy retryPolicy = topicConfiguration.getRetryPolicy();
        TimeUnit.SECONDS.sleep(retryPolicy.getRetryDelay());
        kafkaMessageHandler.process(kafkaMessage);

    }

    private TopicConfiguration getTopicConfiguration(String kafkaTopicName) {
        Optional<String> optionalTopicName = kafkaTopicConfig.getTopics().entrySet().stream()
                .filter( e -> kafkaTopicName.equals(e.getValue().getTopicName())).map(Map.Entry::getKey).findFirst();
        return kafkaTopicConfig.getTopics().get(optionalTopicName.get());
    }


}
