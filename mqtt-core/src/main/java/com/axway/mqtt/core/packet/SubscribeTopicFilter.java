package com.axway.mqtt.core.packet;

/**
 * Created by vchauhan on 9/14/17.
 */
public class SubscribeTopicFilter
{
    private String topicFilter;
    private int qos;

    public SubscribeTopicFilter()
    {
    }

    public SubscribeTopicFilter(String topic)
    {
        this.topicFilter = topic;
    }

    public SubscribeTopicFilter(String topic, int qos)
    {
        this.topicFilter = topic;
        this.qos = qos;
    }

    public String getTopicFilter() {
        return topicFilter;
    }

    public void setTopicFilter(String topicFilter) {
        this.topicFilter = topicFilter;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }
}
