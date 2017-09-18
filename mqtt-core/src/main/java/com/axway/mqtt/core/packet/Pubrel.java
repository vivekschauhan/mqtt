package com.axway.mqtt.core.packet;

import com.axway.mqtt.core.MqttPacket;

/**
 * Created by vchauhan on 9/14/17.
 */
public class Pubrel extends Packet
{
    private int packetId;

    public Pubrel() {
        super(MqttPacket.PUBREL);
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }
}
