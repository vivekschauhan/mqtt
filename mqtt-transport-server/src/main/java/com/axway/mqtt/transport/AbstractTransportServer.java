package com.axway.mqtt.transport;

import com.axway.mqtt.core.packet.Packet;

import java.io.IOException;

/**
 * Created by vchauhan on 9/21/17.
 */
public abstract class AbstractTransportServer implements Transport
{
    public Packet read() throws IOException
    {
        throw new IOException("Unexpected operation");
    }

    public void write(Packet packet) throws IOException
    {
        throw new IOException("Unexpected operation");
    }
}
