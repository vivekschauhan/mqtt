package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Publish;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PublishWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(PublishWriter.class);

    public PublishWriter(Packet packet)
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

    protected byte getPacketTypeFlag()
    {
        byte packetTypeFlag = 0;
        if(getPacket().isRetain())
            packetTypeFlag = Util.setBitOn(packetTypeFlag,0);

        if (getPacket().getQos() == 1)
            packetTypeFlag = Util.setBitOn(packetTypeFlag,1);

        if (getPacket().getQos() == 2)
            packetTypeFlag = Util.setBitOn(packetTypeFlag,2);

        if(getPacket().isDup())
            packetTypeFlag = Util.setBitOn(packetTypeFlag,3);

        return packetTypeFlag;
    }

    protected void writeVariableHeader(OutputStream out) throws IOException
    {
        writeString(out, getPacket().getTopic(), "Topic");
        writeInt(out, getPacket().getPacketId(), "Packet Identifier");
    }

    protected void writePayload(OutputStream out) throws IOException
    {
        writeBytes(out, getPacket().getPayload(), "Application Message");
    }
}
