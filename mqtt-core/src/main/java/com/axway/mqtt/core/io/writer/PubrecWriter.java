package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pubrec;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PubrecWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(PubrecWriter.class);

    public PubrecWriter(Packet packet)
    {
        super(packet);
    }

    public Pubrec getPacket()
    {
        return (Pubrec)packet;
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
