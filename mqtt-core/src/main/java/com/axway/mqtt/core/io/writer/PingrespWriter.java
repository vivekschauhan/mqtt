package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pingresp;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PingrespWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(PingrespWriter.class);

    public PingrespWriter(Packet packet)
    {
        super(packet);
    }

    public Pingresp getPacket()
    {
        return (Pingresp) packet;
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
