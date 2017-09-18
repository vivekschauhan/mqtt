package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Connack;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class ConnackWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(ConnackWriter.class);

    public ConnackWriter(Packet packet)
    {
        super(packet);
    }

    public Connack getPacket()
    {
        return (Connack)packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void writeVariableHeader(OutputStream out) throws IOException
    {
        // Connect Acknowlegment Flags - Session Present
        byte connectAckFlag = 0;
        if(getPacket().isSessionPresent())
            Util.setBitOn(connectAckFlag, 0);
        writeByte(out, connectAckFlag, "Connection Acknowledgement Flag");
        // Return code
        writeByte(out, (byte)getPacket().getReturnCode(), "Return Code");
    }

    protected void writePayload(OutputStream out) throws IOException
    {
        // No Payload
        logger.info("No Payload");
        return;
    }
}
