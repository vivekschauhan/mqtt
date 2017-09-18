package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pubrel;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PubrelReader extends PacketReader
{
    Logger logger = Logger.getLogger(PubrelReader.class);

    public PubrelReader(Packet packet)
    {
        super(packet);
    }

    public Pubrel getPacket()
    {
        return (Pubrel)packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void setPacketTypeFlag(byte packetTypeFlag)
            throws IOException
    {
        if (!Util.isBitOn(packetTypeFlag, 1) ||
                Util.isBitOn(packetTypeFlag, 0) ||
                Util.isBitOn(packetTypeFlag, 2) ||
                Util.isBitOn(packetTypeFlag, 3))
            throw new IOException("Malformed PUBREL packet");
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
