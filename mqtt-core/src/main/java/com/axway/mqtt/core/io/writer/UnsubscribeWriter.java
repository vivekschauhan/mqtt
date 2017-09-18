package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.SubscribeTopicFilter;
import com.axway.mqtt.core.packet.Unsubscribe;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class UnsubscribeWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(UnsubscribeWriter.class);

    public UnsubscribeWriter(Packet packet)
    {
        super(packet);
    }

    public Unsubscribe getPacket()
    {
        return (Unsubscribe) packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected byte getPacketTypeFlag()
    {
        byte packetTypeFlag = 0;
        packetTypeFlag = Util.setBitOn(packetTypeFlag,1);
        return packetTypeFlag;
    }

    protected void writeVariableHeader(OutputStream out) throws IOException
    {
        writeInt(out, getPacket().getPacketId(), "Packet Identifier");
    }

    protected void writePayload(OutputStream out) throws IOException
    {
        int index = 0;
        for(SubscribeTopicFilter topicFilter : getPacket().getTopicFilters())
        {
            writeString(out, topicFilter.getTopicFilter(), "Topic Filter - " + index);
            index++;
        }
        return;
    }
}
