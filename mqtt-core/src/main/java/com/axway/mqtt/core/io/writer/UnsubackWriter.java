package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Unsuback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class UnsubackWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(UnsubackWriter.class);

    public UnsubackWriter(Packet packet)
    {
        super(packet);
    }

    public Unsuback getPacket()
    {
        return (Unsuback)packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }


    protected void writeVariableHeader(OutputStream out) throws IOException
    {
        writeInt(out, getPacket().getPacketId(), "Packet Identifier");
    }

    protected void writePayload(OutputStream out) throws IOException
    {
        // No payload
        return;
    }
}
