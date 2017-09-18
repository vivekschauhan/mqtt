package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pubrec;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PubrecReader extends PacketReader
{
    Logger logger = Logger.getLogger(PubrecReader.class);

    public PubrecReader(Packet packet)
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

    protected void readVariableHeader(InputStream in) throws IOException
    {
        getPacket().setPacketId(readInt(in, "Packet Identifier"));
    }

    protected void readPayload(InputStream in) throws IOException
    {
        // No Payload
        return;
    }
}
