package com.axway.mqtt.core.packet;

import com.axway.mqtt.core.MqttPacket;

/**
 * Created by vchauhan on 9/13/17.
 */
public abstract class Packet
{
    private MqttPacket mqttPacketType;

    public Packet(MqttPacket mqttPacket)
    {
       this.mqttPacketType = mqttPacket;
    }

    public MqttPacket getMqttPacketType() {
        return mqttPacketType;
    }

    public void setMqttPacketType(MqttPacket mqttPacket) {
        this.mqttPacketType = mqttPacket;
    }
}
