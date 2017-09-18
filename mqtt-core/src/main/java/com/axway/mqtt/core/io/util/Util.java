package com.axway.mqtt.core.io.util;

/**
 * Created by vchauhan on 9/13/17.
 */
public class Util
{
    public static boolean isBitOn(byte _byte, int bitPosition)
    {
        _byte >>= (bitPosition);
        return ((_byte & 0x1) == 0x1);
    }

    public final static byte setBitOn(byte _byte, int bitPosition)
    {
        return (byte)(_byte | (1 << (bitPosition)));
    }

    public final static byte setBitOff(byte _byte, int bitPosition)
    {
        return (byte)(_byte &= ~(1 << bitPosition));
    }

    public static String getBitString(byte b) {
        StringBuilder builder = new StringBuilder();
        for (int n = 7; n >= 0; n--)
        {
            if(Util.isBitOn(b, n))
            {
                builder.append('1');
            } else{
                builder.append('0');
            }
        }
        return builder.toString();
    }

    public static String getBytesString(byte bytes[]) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes)
        {
            if (builder.length() > 0)
                builder.append(" ");
            builder.append(getBitString(b));
        }
        return builder.toString();
    }

    private static String getBitString2(byte b) {
        byte[] masks = {-128, 64, 32, 16, 8, 4, 2, 1};

        StringBuilder builder = new StringBuilder();
        for (byte m : masks) {
            if ((b & m) == m) {
                builder.append('1');
            } else {
                builder.append('0');
            }
        }
        return builder.toString();
    }
}
