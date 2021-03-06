package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Suback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class SubackWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(SubackWriter.class);

    public SubackWriter(Packet packet)
    {
        super(packet);
    }

    public Suback getPacket()
    {
        return (Suback) packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void writeVariableHeader(OutputStream out) throws IOException
    {
        writeInt(out, getPacket().getPacketId(), "Packet Identifier");
    }

    protected void writePayload(OutputStream out) throws IOException
    {
        int index = 0;
        for(int returnCodes : getPacket().getReturnCodes())
        {
            writeByte(out, (byte)returnCodes, "Return Code - " + index);
            index++;
        }
        return;
    }
}
