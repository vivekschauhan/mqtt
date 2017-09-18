package com.axway.mqtt.core.packet;

import com.axway.mqtt.core.MqttPacket;

import java.util.ArrayList;

/**
 * Created by vchauhan on 9/14/17.
 */
public class Subscribe extends Packet
{
    private int packetId;

    ArrayList<SubscribeTopicFilter> topicFilters = new ArrayList<>();

    public Subscribe() {
        super(MqttPacket.SUBSCRIBE);
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public ArrayList<SubscribeTopicFilter> getTopicFilters() {
        return topicFilters;
    }

    public void setTopicFilters(ArrayList<SubscribeTopicFilter> topicFilters) {
        this.topicFilters = topicFilters;
    }

    public void addTopicFilters(SubscribeTopicFilter topicFilters) {
        this.topicFilters.add(topicFilters);
    }

    public void addTopicFilters(String topic, int qos) {
        this.topicFilters.add(new SubscribeTopicFilter(topic, qos));
    }
}
