package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Publish;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PublishReader extends PacketReader
{
    Logger logger = Logger.getLogger(PublishReader.class);

    public PublishReader(Packet packet)
    {
        super(packet);
    }

    public Publish getPacket()
    {
        return (Publish)packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void setPacketTypeFlag(byte packetTypeFlag) throws IOException
    {
        getPacket().setRetain(Util.isBitOn(packetTypeFlag, 0));
        if (Util.isBitOn(packetTypeFlag, 1))
            getPacket().setQos(1);
        if (Util.isBitOn(packetTypeFlag, 2))
            getPacket().setQos(2);
        getPacket().setDup(Util.isBitOn(packetTypeFlag, 3));
    }

    protected void readVariableHeader(InputStream in) throws IOException
    {
        getPacket().setTopic(readString(in, "Topic"));
        if(getPacket().getQos() != 0)
            getPacket().setPacketId(readInt(in, "Packet Identifier"));
    }

    protected void readPayload(InputStream in) throws IOException
    {
        getPacket().setPayload(readBytes(in, in.available(), "Application Message (bytes)"));
        getLogger().info("Application Message (String) : " + new String(getPacket().getPayload()));
    }
}
