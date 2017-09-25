package com.axway.mqtt.transport;

import com.axway.mqtt.core.packet.Packet;

import java.io.IOException;

/**
 * Created by vchauhan on 9/19/17.
 */
public interface Transport
{
    boolean isConnected();

    void connect() throws IOException;

    Packet read() throws IOException;

    void write(Packet packet) throws IOException;

    void close(String reason) throws IOException;
}
