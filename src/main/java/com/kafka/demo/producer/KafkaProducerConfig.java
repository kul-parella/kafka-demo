package com.kafka.demo.producer;

import com.kafka.demo.config.KafkaTopicConfig;
import com.kafka.demo.model.KafkaMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    @Bean
    public ProducerFactory<String, KafkaMessage> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaTopicConfig.getBootstrapServersConfig());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaTopicConfig.getKeySerializerClassConfig());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaTopicConfig.getValueDeserializerClassConfig());
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
