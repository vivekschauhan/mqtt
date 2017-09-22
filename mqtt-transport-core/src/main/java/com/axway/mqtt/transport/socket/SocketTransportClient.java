package com.axway.mqtt.transport.socket;

import com.axway.mqtt.transport.CallbackRegistry;
import com.axway.mqtt.transport.TransportCallback;
import com.axway.mqtt.transport.Transport;
import com.axway.mqtt.core.MqttPacket;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.transport.config.SocketTransportConfig;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by vchauhan on 9/19/17.
 */
public class SocketTransportClient implements Transport
{
    static Logger logger = Logger.getLogger(SocketTransportClient.class);
    private SocketChannel channel;
    private SocketAddress clientAddress;

    public SocketTransportClient(SocketTransportConfig transportConfig)
    {
        this.clientAddress = new InetSocketAddress(transportConfig.getHost(), transportConfig.getPort());
    }

    SocketTransportClient(SelectionKey key) throws IOException
    {
        channel = (SocketChannel)key.channel();
        clientAddress = channel.getRemoteAddress();
    }

    public boolean isConnected()
    {
        return (channel != null && channel.isConnected());
    }

    public void connect() throws IOException
    {
        if (!isConnected())
        {
            if(clientAddress != null)
            {
                channel = SocketChannel.open();
                channel.connect(clientAddress);
                while(!channel.finishConnect() ){
                    logger.info("Trying to connect");
                }
                return;
            }
            else
                throw new IOException("No client address");
        }
        logger.info("Already connected");
    }

    public Packet read() throws IOException
    {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        int dataLen = 0;
        byte[] data = null;
        do
        {
            try
            {
                buffer.clear();
                int readCount = channel.read(buffer);
                buffer.flip();
                if (readCount == -1)
                {
                    Socket clientSocket = channel.socket();
                    SocketAddress remoteAddress = clientSocket.getRemoteSocketAddress();
                    logger.info("Closing connection for client " + remoteAddress);
                    close();
                    throw new IOException("Connection closed");
                }
                dataLen += readCount;
                if(data == null)
                {
                    data = new byte[readCount];
                    System.arraycopy(buffer.array(), 0, data, 0, dataLen);
                }
                else
                {
                    byte[] newData = new byte[dataLen];
                    System.arraycopy(data, 0, newData, 0, data.length);
                    System.arraycopy(buffer.array(), 0, newData, data.length, readCount);
                    data = newData;
                }

                Packet packet = MqttPacket.readPacket(new ByteArrayInputStream(data));
                for(TransportCallback transportCallback : CallbackRegistry.getCallbacks())
                {
                    if (transportCallback != null)
                        transportCallback.onPacketReceive(packet, this);
                }
                return packet;
            }
            catch (IOException e)
            {
                if (e.getMessage().equals("Incomplete packet"))
                    continue;
                else
                    throw e;
            }
        } while (true);
    }

    public void write(Packet packet) throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MqttPacket.writePacket(packet, bos);
        ByteBuffer buffer = ByteBuffer.wrap(bos.toByteArray());
        channel.write(buffer);
        buffer.clear();

        for(TransportCallback transportCallback: CallbackRegistry.getCallbacks())
        {
            if (transportCallback != null)
                transportCallback.onPacketSend(packet, this);
        }
    }

    public void close() throws IOException
    {
        channel.close();
    }
}
