package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Unsubscribe;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class UnsubscribeReader extends PacketReader
{
    Logger logger = Logger.getLogger(UnsubscribeReader.class);

    public UnsubscribeReader(Packet packet)
    {
        super(packet);
    }

    public Unsubscribe getPacket()
    {
        return (Unsubscribe)packet;
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
            throw new IOException("Malformed UNSUBSCRIBE packet");
    }

    protected void readVariableHeader(InputStream in) throws IOException
    {
        getPacket().setPacketId(readInt(in, "Packet Identifier"));
    }

    protected void readPayload(InputStream in) throws IOException
    {
        int index = 0;
        while(in.available() > 0)
        {
            String topic = readString(in, "Topic Filter - " + index);
            getPacket().addTopicFilters(topic);
            index++;
        }
        return;
    }
}
