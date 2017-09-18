package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Connect;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public class ConnectWriter extends PacketWriter
{
    Logger logger = Logger.getLogger(ConnectWriter.class);

    static byte protocolLevel = 0x04;

    public ConnectWriter(Packet packet)
    {
        super(packet);
    }

    protected Connect getPacket()
    {
        return (Connect)packet;
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void writeVariableHeader(OutputStream out)
            throws IOException {
        // Protocol Name - 6 bytes
        writeString(out, "MQTT", "Protocol Name");
        // Protocol Level - 1 byte
        writeByte(out, protocolLevel, "Protocol Level");
        // Connection flags - 1 byte
        writeConnectionFlags(out);
        // Keep Alive  - 2 byte
        writeInt(out, getPacket().getKeepAlive(), "Keep Alive");
    }

    protected void writeConnectionFlags(OutputStream out)
            throws IOException {
        byte connectFlag = 0;
        if (getPacket().isCleanSession())
            connectFlag = Util.setBitOn(connectFlag,1);
        getLogger().info("Clean Session : " + getPacket().isCleanSession());

        if (getPacket().hasWillFlag())
            connectFlag = Util.setBitOn(connectFlag,2);
        getLogger().info("Will Flag: " + getPacket().hasWillFlag());

        if (getPacket().getWillQos() != 0) {
            if (getPacket().getWillQos() == 1)
                connectFlag = Util.setBitOn(connectFlag,3);
            if (getPacket().getWillQos() == 2)
                connectFlag = Util.setBitOn(connectFlag,4);
        }
        getLogger().info("Will Qos: " + getPacket().getWillQos());

        if (getPacket().hasWillRetain())
            connectFlag = Util.setBitOn(connectFlag,5);
        getLogger().info("Will Retain: " + getPacket().hasWillRetain());

        if (getPacket().hasPassword())
            connectFlag = Util.setBitOn(connectFlag,6);
        getLogger().info("Has Password : " + getPacket().hasPassword());

        if (getPacket().hasUserName())
            connectFlag = Util.setBitOn(connectFlag,7);
        getLogger().info("Has UserName : " + getPacket().hasUserName());

        writeByte(out, (byte)connectFlag, "Connection Flag");
    }

    protected void writePayload(OutputStream out)
            throws IOException {
        // Client Id
        writeString(out, getPacket().getClientId(), "Client Id");
        if (getPacket().hasWillFlag()) {
            // Will Topic
            writeString(out, getPacket().getTopic(), "Topic");
            // Will Message
            writeString(out, getPacket().getMessage(), "Message");
        }
        // User name
        if (getPacket().hasUserName())
            writeString(out, getPacket().getUserName(), "Username");
        // Password
        if (getPacket().hasPassword())
            writeString(out, getPacket().getPassword(), "Password");
    }

}
