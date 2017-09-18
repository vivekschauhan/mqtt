package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Subscribe;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class SubscribeReader extends PacketReader
{
    Logger logger = Logger.getLogger(SubscribeReader.class);

    public SubscribeReader(Packet packet)
    {
        super(packet);
    }

    public Subscribe getPacket()
    {
        return (Subscribe)packet;
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
            throw new IOException("Malformed SUBSCRIBE packet");
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
            byte requestedQos = readByte(in, "Requested Qos - " + index);
            int qos = 0;
            if(Util.isBitOn(requestedQos, 0))
                qos = 1;
            if(Util.isBitOn(requestedQos, 1))
                qos = 2;
            getPacket().addTopicFilters(topic, qos);
            index++;
        }
        return;
    }
}
