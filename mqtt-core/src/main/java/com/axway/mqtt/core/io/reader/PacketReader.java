package com.axway.mqtt.core.io.reader;

import com.axway.mqtt.core.MqttPacket;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public abstract class PacketReader
{
    Logger logger = Logger.getLogger(PacketReader.class);

    protected Packet packet;
    protected int remainingLen;

    protected abstract void readVariableHeader(InputStream in) throws IOException;
    protected abstract void readPayload(InputStream in) throws IOException;

    public PacketReader(Packet packet)
    {
        this.packet = packet;
    }

    public Packet getPacket()
    {
        return packet;
    }

    public final static byte getPacketType(byte packetTypeAndFlags)
            throws IOException
    {
        packetTypeAndFlags >>= 4 ;
        packetTypeAndFlags = Util.setBitOff(packetTypeAndFlags, 4);
        packetTypeAndFlags = Util.setBitOff(packetTypeAndFlags, 5);
        packetTypeAndFlags = Util.setBitOff(packetTypeAndFlags, 6);
        packetTypeAndFlags = Util.setBitOff(packetTypeAndFlags, 7);

        return packetTypeAndFlags;
    }

    public final void read(InputStream inputStream)
            throws IOException
    {
        // Fix Header - Already processed
        // - com.axway.mqtt.common.Packet Type and Flags
        byte packetTypeAndFlags = readByte(inputStream, "Packet Type and Flags");
        if(MqttPacket.forPacketType(getPacketType(packetTypeAndFlags)) != getPacket().getMqttPacketType())
            throw new IOException("Unexpected packet type");

        setPacketTypeFlag(packetTypeAndFlags);
        // - Remaining Length
        remainingLen = readRemainingLength(inputStream);
        // Variable Header
        readVariableHeader(inputStream);
        // com.axway.mqtt.common.Payload
        readPayload(inputStream);
    }

    protected void setPacketTypeFlag(byte packetTypeFlag) throws IOException
    {
        // No processing
        return;
    }

    protected int readInt(InputStream in)
            throws IOException
    {
        return readInt(in, null);
    }

    protected int readInt(InputStream in, String logMessage)
            throws IOException
    {
        int intValue = 0;
        byte intMSB = (byte)in.read();
        byte intLSB = (byte)in.read();
        intValue = intMSB;
        intValue <<=8;
        intValue |= intLSB;

        if(logMessage != null)
            getLogger().info(logMessage + " : " + intValue + " [MSB(" + (Util.getBitString(intMSB) + ") LSB(" + Util.getBitString(intLSB)+ ")]"));

        return intValue;
    }

    protected String readString(InputStream in)
            throws IOException
    {
        return readString(in, null);
    }

    protected String readString(InputStream in, String logMessage)
            throws IOException
    {
        int len = readInt(in);
        byte[] string = new byte[len];
        in.read(string);

        String str = new String(string, "UTF-8");
        if(logMessage != null)
            getLogger().info(logMessage + " : " + str);

        return str;
    }

    protected byte readByte(InputStream in)
            throws IOException
    {
        return readByte(in, null);
    }

    protected byte readByte(InputStream in, String logMessage)
            throws IOException
    {
        byte byteValue = (byte)in.read();
        if(logMessage != null)
            getLogger().info(logMessage + " : " + Util.getBitString(byteValue));

        return byteValue;
    }

    protected byte[] readBytes(InputStream in, int length)
            throws IOException
    {
        return readBytes(in, length, null);
    }

    protected byte[] readBytes(InputStream in, int length, String logMessage)
            throws IOException
    {
        byte bytes[] = new byte[length];
        in.read(bytes);
        if(logMessage != null)
            getLogger().info(logMessage + " : " + Util.getBytesString(bytes));

        return bytes;
    }

    private int readRemainingLength(InputStream in)
            throws IOException
    {
        int len = 0;
        int multiplier = 1;
        byte digit = 0;
        do
        {
            digit = (byte)in.read();
            len += (digit & 127) * multiplier;
            multiplier *= 128;
        } while((digit & 128) != 0);
        return len;
    }

    protected Logger getLogger()
    {
        return logger;
    }
}
