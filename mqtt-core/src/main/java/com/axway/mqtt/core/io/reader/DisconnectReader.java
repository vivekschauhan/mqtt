package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Disconnect;
import com.axway.mqtt.core.packet.Packet;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class DisconnectReader extends PacketReader
{
    Logger logger = Logger.getLogger(DisconnectReader.class);

    public DisconnectReader(Packet packet)
    {
        super(packet);
    }

    public Disconnect getPacket()
    {
        return (Disconnect) packet;
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
