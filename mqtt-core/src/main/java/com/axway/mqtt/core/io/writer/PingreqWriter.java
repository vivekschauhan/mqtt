package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pingreq;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PingreqWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(PingreqWriter.class);

    public PingreqWriter(Packet packet)
    {
        super(packet);
    }

    public Pingreq getPacket()
    {
        return (Pingreq) packet;
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
