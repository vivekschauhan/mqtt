package com.axway.mqtt.core.packet;

import com.axway.mqtt.core.MqttPacket;

/**
 * Created by vchauhan on 9/14/17.
 */
public class Pubcomp extends Packet
{
    private int packetId;

    public Pubcomp() {
        super(MqttPacket.PUBCOMP);
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }
}
