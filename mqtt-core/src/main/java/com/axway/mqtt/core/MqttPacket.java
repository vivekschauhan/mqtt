package com.axway.mqtt.core;

import com.axway.mqtt.core.io.reader.ConnectReader;
import com.axway.mqtt.core.io.reader.ConnackReader;
import com.axway.mqtt.core.io.reader.DisconnectReader;
import com.axway.mqtt.core.io.reader.PacketReader;
import com.axway.mqtt.core.io.reader.PingreqReader;
import com.axway.mqtt.core.io.reader.PingrespReader;
import com.axway.mqtt.core.io.reader.PubackReader;
import com.axway.mqtt.core.io.reader.PubcompReader;
import com.axway.mqtt.core.io.reader.PublishReader;
import com.axway.mqtt.core.io.reader.PubrecReader;
import com.axway.mqtt.core.io.reader.PubrelReader;
import com.axway.mqtt.core.io.reader.SubackReader;
import com.axway.mqtt.core.io.reader.SubscribeReader;
import com.axway.mqtt.core.io.reader.UnsubackReader;
import com.axway.mqtt.core.io.reader.UnsubscribeReader;
import com.axway.mqtt.core.io.writer.ConnectWriter;
import com.axway.mqtt.core.io.writer.ConnackWriter;
import com.axway.mqtt.core.io.writer.DisconnectWriter;
import com.axway.mqtt.core.io.writer.PacketWriter;
import com.axway.mqtt.core.io.writer.PingreqWriter;
import com.axway.mqtt.core.io.writer.PingrespWriter;
import com.axway.mqtt.core.io.writer.PubackWriter;
import com.axway.mqtt.core.io.writer.PubcompWriter;
import com.axway.mqtt.core.io.writer.PublishWriter;
import com.axway.mqtt.core.io.writer.PubrecWriter;
import com.axway.mqtt.core.io.writer.PubrelWriter;
import com.axway.mqtt.core.io.writer.SubackWriter;
import com.axway.mqtt.core.io.writer.SubscribeWriter;
import com.axway.mqtt.core.io.writer.UnsubackWriter;
import com.axway.mqtt.core.io.writer.UnsubscribeWriter;
import com.axway.mqtt.core.packet.Connect;
import com.axway.mqtt.core.packet.Connack;
import com.axway.mqtt.core.packet.Disconnect;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pingreq;
import com.axway.mqtt.core.packet.Pingresp;
import com.axway.mqtt.core.packet.Puback;
import com.axway.mqtt.core.packet.Pubcomp;
import com.axway.mqtt.core.packet.Publish;
import com.axway.mqtt.core.packet.Pubrec;
import com.axway.mqtt.core.packet.Pubrel;
import com.axway.mqtt.core.packet.Suback;
import com.axway.mqtt.core.packet.Subscribe;
import com.axway.mqtt.core.packet.Unsuback;
import com.axway.mqtt.core.packet.Unsubscribe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by vchauhan on 9/13/17.
 */
public enum MqttPacket
{
    CONNECT(1)
        {
            protected PacketReader getReader()
            {
                return new ConnectReader(new Connect());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new ConnectWriter(packet);
            }
        },
    CONNACK(2)
        {
            protected PacketReader getReader()
            {
                return new ConnackReader(new Connack());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new ConnackWriter(packet);
            }
        },
    PUBLISH(3)
        {
            protected PacketReader getReader()
            {
                return new PublishReader(new Publish());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new PublishWriter(packet);
            }
        },
    PUBACK(4)
        {
            protected PacketReader getReader()
            {
                return new PubackReader(new Puback());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new PubackWriter(packet);
            }
        },
    PUBREC(5)
        {
            protected PacketReader getReader()
            {
                return new PubrecReader(new Pubrec());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new PubrecWriter(packet);
            }
        },
    PUBREL(6)
        {
            protected PacketReader getReader()
            {
                return new PubrelReader(new Pubrel());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new PubrelWriter(packet);
            }
        },
    PUBCOMP(7)
        {
            protected PacketReader getReader()
            {
                return new PubcompReader(new Pubcomp());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new PubcompWriter(packet);
            }
        },
    SUBSCRIBE(8)
        {
            protected PacketReader getReader()
            {
                return new SubscribeReader(new Subscribe());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new SubscribeWriter(packet);
            }
        },
    SUBACK(9)
        {
            protected PacketReader getReader()
            {
                return new SubackReader(new Suback());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new SubackWriter(packet);
            }
        },
    UNSUBSCRIBE(10)
        {
            protected PacketReader getReader()
            {
                return new UnsubscribeReader(new Unsubscribe());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new UnsubscribeWriter(packet);
            }
        },
    UNSUBACK(11)
        {
            protected PacketReader getReader()
            {
                return new UnsubackReader(new Unsuback());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new UnsubackWriter(packet);
            }
        },
    PINGREQ(12)
        {
            protected PacketReader getReader()
            {
                return new PingreqReader(new Pingreq());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new PingreqWriter(packet);
            }
        },
    PINGRESP(13)
        {
            protected PacketReader getReader()
            {
                return new PingrespReader(new Pingresp());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new PingrespWriter(packet);
            }
        },
    DISCONNECT(14)
        {
            protected PacketReader getReader()
            {
                return new DisconnectReader(new Disconnect());
            }

            protected PacketWriter getWriter(Packet packet)
            {
                return new DisconnectWriter(packet);
            }
        };

    private int packetType;

    MqttPacket(int packetType)
    {
        this.packetType = packetType;
    }

    public int getValue()
    {
        return packetType;
    }
    protected abstract PacketReader getReader();
    protected abstract PacketWriter getWriter(Packet packet);

    public final static Packet readPacket(InputStream inputStream)
            throws IOException
    {
        inputStream.mark(0);
        byte packetTypeAndFlags = (byte)inputStream.read();
        int packetType = PacketReader.getPacketType(packetTypeAndFlags);
        inputStream.reset();

        MqttPacket mqttPacket = MqttPacket.forPacketType(packetType);
        Packet packet = null;
        if(mqttPacket != null)
        {
            PacketReader packetReader = mqttPacket.getReader();
            packetReader.read(inputStream);
            packet = packetReader.getPacket();
        }
        return packet;
    }

    public final static void writePacket(Packet packet, OutputStream outputStream)
            throws IOException
    {
        PacketWriter packetWriter = packet.getMqttPacketType().getWriter(packet);
        packetWriter.write(outputStream);
    }

    public static MqttPacket forPacketType(int packetType)
    {
        for (MqttPacket typе : MqttPacket.values())
        {
            if (typе.getValue() == packetType)
                return typе;
        }
        return null;
    }
}
