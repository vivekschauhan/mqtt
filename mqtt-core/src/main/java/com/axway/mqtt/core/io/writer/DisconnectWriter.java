package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Disconnect;
import com.axway.mqtt.core.packet.Packet;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class DisconnectWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(DisconnectWriter.class);

    public DisconnectWriter(Packet packet)
    {
        super(packet);
    }

    public Disconnect getPacket()
    {
        return (Disconnect) packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void writeVariableHeader(OutputStream out) throws IOException
    {
        // No Variable header
        return;
    }

    protected void writePayload(OutputStream out) throws IOException
    {
        // No payload
        return;
    }
}
