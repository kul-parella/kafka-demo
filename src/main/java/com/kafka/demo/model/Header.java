package com.kafka.demo.model;

public class Header {

    private boolean isRetry;

    private int currentRetryCount;

    private String topicName;

    private String nextTopicName;

    public Header() {
    }

    public Header(boolean isRetry, int currentRetryCount, String topicName, String nextTopicName) {
        this.isRetry = isRetry;
        this.currentRetryCount = currentRetryCount;
        this.topicName = topicName;
        this.nextTopicName = nextTopicName;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean retry) {
        isRetry = retry;
    }

    public int getCurrentRetryCount() {
        return currentRetryCount;
    }

    public void setCurrentRetryCount(int currentRetryCount) {
        this.currentRetryCount = currentRetryCount;
    }

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
}
