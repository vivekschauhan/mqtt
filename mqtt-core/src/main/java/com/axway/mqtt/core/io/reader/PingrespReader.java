package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pingresp;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class PingrespReader extends PacketReader
{
    Logger logger = Logger.getLogger(PingrespReader.class);

    public PingrespReader(Packet packet)
    {
        super(packet);
    }

    public Pingresp getPacket()
    {
        return (Pingresp) packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void readVariableHeader(InputStream in) throws IOException
    {
        // No Variable header
        return;
    }

    protected void readPayload(InputStream in) throws IOException
    {
        // No Payload
        return;
    }
}
