package com.axway.mqtt.core.io.writer;

import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.io.util.Util;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/14/17.
 */
public abstract class PacketWriter
{
    Logger logger = Logger.getLogger(PacketWriter.class);

    protected Packet packet;

    protected abstract void writeVariableHeader(OutputStream out) throws IOException;
    protected abstract void writePayload(OutputStream out) throws IOException;

    public PacketWriter(Packet packet)
    {
        this.packet = packet;
    }

    public final void write(OutputStream out)
            throws IOException
    {
        // Fix Header
        // - com.axway.mqtt.common.Packet Type
        byte packetTypeAndFlags = (byte)packet.getMqttPacketType().getValue();
        packetTypeAndFlags <<= 4;
        // - com.axway.mqtt.common.Packet Flag
        packetTypeAndFlags |= getPacketTypeFlag();
        writeByte(out, packetTypeAndFlags, "Packet Type and Flags");

        // Get variable header and payload
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        writeVariableHeader(stream);
        writePayload(stream);
        byte[] variableHeaderAndPayload = stream.toByteArray();

        // Remaining Length
        writeRemainingLength(out, variableHeaderAndPayload.length);
        // Variable Header + com.axway.mqtt.common.Payload
        out.write(variableHeaderAndPayload);
    }

    private void writeRemainingLength(OutputStream out, int len)
            throws IOException
    {

        do
        {
            byte encodedLen = (byte) (len % 128);
            len /= 128;
            if (len > 0)
                encodedLen = (byte) (encodedLen | 128);
            out.write(encodedLen);
        } while(len > 0);
    }

    protected Logger getLogger()
    {
        return logger;
    }

    protected void writeInt(OutputStream out, int intValue)
            throws IOException
    {
        writeInt(out, intValue, null);
    }

    protected byte getPacketTypeFlag()
    {
        return 0;
    }

    protected void writeInt(OutputStream out, int intValue, String logMessage)
            throws IOException
    {
        int value = intValue;
        byte intLSB = (byte) (value & 0xff);
        value >>= 8;
        byte intMSB = (byte) value;
        out.write(intMSB);
        out.write(intLSB);
        if(logMessage != null)
            getLogger().info(logMessage + " : " + intValue + " [MSB(" + (Util.getBitString(intMSB) + ") LSB(" + Util.getBitString(intLSB)+ ")]"));
    }

    protected void writeString(OutputStream out, String str)
            throws IOException
    {
        writeString(out, str, null);
    }

    protected void writeString(OutputStream out, String str, String logMessage)
            throws IOException
    {
        writeInt(out, str.getBytes("UTF-8").length);
        out.write(str.getBytes("UTF-8"));
        if(logMessage != null)
            getLogger().info(logMessage + " : " + str);
    }

    protected void writeByte(OutputStream out, byte byteValue)
            throws IOException
    {
        writeByte(out, byteValue, null);
    }

    protected void writeByte(OutputStream out, byte byteValue, String logMessage)
            throws IOException
    {
        out.write(byteValue);
        if(logMessage != null)
            getLogger().info(logMessage + " : " + Util.getBitString(byteValue));
    }

    protected void writeBytes(OutputStream out, byte[] bytes)
            throws IOException
    {
        writeBytes(out, bytes, null);
    }

    protected void writeBytes(OutputStream out, byte[] bytes, String logMessage)
            throws IOException
    {
        out.write(bytes);
        if(logMessage != null)
            getLogger().info(logMessage + " : " + Util.getBytesString(bytes));
    }

}
