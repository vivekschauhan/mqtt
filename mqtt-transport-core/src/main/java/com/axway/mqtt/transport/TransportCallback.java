package com.axway.mqtt.transport;

import com.axway.mqtt.core.packet.Packet;

/**
 * Created by vchauhan on 9/19/17.
 */
public interface TransportCallback
{
    void onPacketReceive(Packet packet, Transport client);
    void onPacketSend(Packet packet, Transport client);
}
