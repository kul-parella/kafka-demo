package com.kafka.demo.listener;

import com.kafka.demo.config.KafkaTopicConfig;
import com.kafka.demo.config.TopicConfiguration;
import com.kafka.demo.handler.KafkaExceptionHandler;
import com.kafka.demo.model.KafkaMessage;
import com.kafka.demo.producer.KafkaProducer;
import com.kafka.demo.util.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaListenerconfig {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    @Bean
    public ConsumerFactory<String, KafkaMessage> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        TopicConfiguration topicConfiguration = kafkaTopicConfig.getTopics().get(KafkaConstants.ORIGINAL_TOPIC);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaTopicConfig.getBootstrapServersConfig());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaTopicConfig.getConsumerGroupName());
        config.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "100000");
        try {
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaTopicConfig.getKeyDeserializerClassConfig());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaTopicConfig.getValueDeserializerClassConfig());
        }catch (Exception ex) {
            System.out.println("class not configured right for deserialization" + ex);
        }
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(KafkaMessage.class));

    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaMessage>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new KafkaExceptionHandler(kafkaProducer, kafkaTopicConfig));
        factory.setConcurrency(Integer.parseInt(kafkaTopicConfig.getTopics()
                .get(KafkaConstants.ORIGINAL_TOPIC).getMaxconsumers()));
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(KafkaTopicConfig.class)
    KafkaTopicConfig kafkaTopicConfig() {
        return  new KafkaTopicConfig();
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaMessage>>
    kafkaDelay1ListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new KafkaExceptionHandler(kafkaProducer, kafkaTopicConfig));
        factory.setConcurrency(Integer.parseInt(kafkaTopicConfig.getTopics()
                .get(KafkaConstants.DELAY_TOPIC_1).getMaxconsumers()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaMessage>>
    kafkaDelay2ListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new KafkaExceptionHandler(kafkaProducer, kafkaTopicConfig));
        factory.setConcurrency(Integer.parseInt(kafkaTopicConfig.getTopics()
                .get(KafkaConstants.DELAY_TOPIC_2).getMaxconsumers()));
        return factory;
    }

}
