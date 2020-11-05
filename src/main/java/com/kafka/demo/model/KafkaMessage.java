package com.kafka.demo.model;

import java.util.Date;

public class KafkaMessage {

    private Long id;

    private String eventType;

    private Date eventTimeStamp;

    private String payLoad;

    private Header header;

    public KafkaMessage() {
    }

    public KafkaMessage(Long id, String eventType, Date eventTimeStamp, String payLoad, Header header) {
        this.id = id;
        this.eventType = eventType;
        this.eventTimeStamp = eventTimeStamp;
        this.payLoad = payLoad;
        this.header = header;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventTimeStamp() {
        return eventTimeStamp;
    }

    public void setEventTimeStamp(Date eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
