package com.kafka.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@ConfigurationProperties(prefix = "spring.demo.kafka")
@Configuration
public class KafkaTopicConfig {

    private String bootstrapServersConfig;

    private String consumerGroupName;

    private String enableListeners;

    private String keySerializerClassConfig;

    private String keyDeserializerClassConfig;

    private String valueDeserializerClassConfig;

    private String nonRecoverableTopicName;

    private String finalErrorTopicName;

    private Map<String, TopicConfiguration> topics = new HashMap<>();

    public String getBootstrapServersConfig() {
        return bootstrapServersConfig;
    }

    public void setBootstrapServersConfig(String bootstrapServersConfig) {
        this.bootstrapServersConfig = bootstrapServersConfig;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
    }

    public String getEnableListeners() {
        return enableListeners;
    }

    public void setEnableListeners(String enableListeners) {
        this.enableListeners = enableListeners;
    }

    public String getKeySerializerClassConfig() {
        return keySerializerClassConfig;
    }

    public void setKeySerializerClassConfig(String keySerializerClassConfig) {
        this.keySerializerClassConfig = keySerializerClassConfig;
    }

    public String getKeyDeserializerClassConfig() {
        return keyDeserializerClassConfig;
    }

    public void setKeyDeserializerClassConfig(String keyDeserializerClassConfig) {
        this.keyDeserializerClassConfig = keyDeserializerClassConfig;
    }

    public String getValueDeserializerClassConfig() {
        return valueDeserializerClassConfig;
    }

    public void setValueDeserializerClassConfig(String valueDeserializerClassConfig) {
        this.valueDeserializerClassConfig = valueDeserializerClassConfig;
    }

    public String getNonRecoverableTopicName() {
        return nonRecoverableTopicName;
    }

    public void setNonRecoverableTopicName(String nonRecoverableTopicName) {
        this.nonRecoverableTopicName = nonRecoverableTopicName;
    }

    public String getFinalErrorTopicName() {
        return finalErrorTopicName;
    }

    public void setFinalErrorTopicName(String finalErrorTopicName) {
        this.finalErrorTopicName = finalErrorTopicName;
    }

    public Map<String, TopicConfiguration> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, TopicConfiguration> topics) {
        this.topics = topics;
    }
}
