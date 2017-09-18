package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Suback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class SubackReader extends PacketReader
{
    Logger logger = Logger.getLogger(SubackReader.class);

    public SubackReader(Packet packet)
    {
        super(packet);
    }

    public Suback getPacket()
    {
        return (Suback)packet;
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
        int index = 0;
        while(in.available() > 0)
        {
            byte returnCode = readByte(in, "Return Code - " + index);
            getPacket().addReturnCode(returnCode);
            index++;
        }
        return;
    }
}
