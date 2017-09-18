package com.axway.mqtt.core.packet;

import com.axway.mqtt.core.MqttPacket;

/**
 * Created by vchauhan on 9/14/17.
 */
public class Disconnect extends Packet
{
    public Disconnect() {
        super(MqttPacket.DISCONNECT);
    }
}
