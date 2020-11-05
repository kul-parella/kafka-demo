package com.kafka.demo.config;

public class TopicConfiguration {

    private String topicName;

    private String nextTopicName;

    private String maxconsumers;

    private RetryPolicy retryPolicy;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getNextTopicName() {
        return nextTopicName;
    }

    public void setNextTopicName(String nextTopicName) {
        this.nextTopicName = nextTopicName;
    }

    public String getMaxconsumers() {
        return maxconsumers;
    }

    public void setMaxconsumers(String maxconsumers) {
        this.maxconsumers = maxconsumers;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }
}
