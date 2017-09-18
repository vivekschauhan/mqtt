package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Connack;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class ConnackReader extends PacketReader
{
    Logger logger = Logger.getLogger(ConnackReader.class);
    public ConnackReader(Packet packet)
    {
        super(packet);
    }

    public Connack getPacket()
    {
        return (Connack) packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void readVariableHeader(InputStream in) throws IOException
    {
        byte connectAckFlag = readByte(in, "Connection Acknowledgement Flag");
        getPacket().setSessionPresent(Util.isBitOn(connectAckFlag, 0));
        getPacket().setReturnCode(readByte(in, "Return Code"));
    }

    protected void readPayload(InputStream in) throws IOException
    {
        getLogger().info("No Payload");
        return;
    }

}
