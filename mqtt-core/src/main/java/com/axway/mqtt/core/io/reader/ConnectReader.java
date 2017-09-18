package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.packet.Connect;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class ConnectReader extends PacketReader
{
    Logger logger = Logger.getLogger(ConnectReader.class);

    public ConnectReader(Packet packet)
    {
        super(packet);
    }

    public Connect getPacket()
    {
        return (Connect)packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void readVariableHeader(InputStream in)
            throws IOException {
        String protocolName = readString(in, "Protocol Name");
        int protocolLevel = readByte(in, "Protocol Level");
        readConnectionFlags(in);
        getPacket().setKeepAlive(readInt(in));
    }

    protected void readConnectionFlags(InputStream in)
            throws IOException {
        byte connectFlag = readByte(in, "Connection Flag");

        getPacket().setCleanSession((Util.isBitOn(connectFlag, 1)));
        getLogger().info("Clean Session : " + getPacket().isCleanSession());

        getPacket().setWillFlag((Util.isBitOn(connectFlag, 2)));
        getLogger().info("Will Flag: " + getPacket().hasWillFlag());

        if (Util.isBitOn(connectFlag, 3))
            getPacket().setWillQos(1);
        if (Util.isBitOn(connectFlag, 4))
            getPacket().setWillQos(2);
        getLogger().info("Will Qos: " + getPacket().getWillQos());

        getPacket().setWillRetain(Util.isBitOn(connectFlag, 5));
        getLogger().info("Will Retain: " + getPacket().hasWillRetain());

        getPacket().setHasPassword((Util.isBitOn(connectFlag, 6)));
        getLogger().info("Has Password : " + getPacket().hasPassword());
        getPacket().setHasUserName(Util.isBitOn(connectFlag, 7));
        getLogger().info("Has UserName : " + getPacket().hasUserName());
    }

    protected void readPayload(InputStream in)
            throws IOException
    {
        getPacket().setClientId(readString(in, "Client Id"));
        if(getPacket().hasWillFlag())
        {
            getPacket().setTopic(readString(in, "Topic"));
            getPacket().setMessage(readString(in, "Message"));
        }
        if(getPacket().hasUserName())
            getPacket().setUserName(readString(in, "Username"));
        if(getPacket().hasPassword())
            getPacket().setPassword(readString(in, "Password"));
    }
}
