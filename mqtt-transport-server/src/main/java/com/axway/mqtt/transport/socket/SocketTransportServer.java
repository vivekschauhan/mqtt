package com.axway.mqtt.transport.socket;

import com.axway.mqtt.transport.AbstractTransportServer;
import com.axway.mqtt.transport.Transport;
import com.axway.mqtt.transport.config.SocketTransportConfig;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * Created by vchauhan on 9/19/17.
 */
public class SocketTransportServer extends AbstractTransportServer implements Runnable
{
    static Logger logger = Logger.getLogger(SocketTransportServer.class);

    private SocketTransportConfig transportConfig;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private HashMap<SocketChannel, Transport> transportClientsMap = new HashMap<>();

    public SocketTransportServer(SocketTransportConfig transportConfig)
    {
        this.transportConfig = transportConfig;
    }

    public void connect() throws IOException
    {
        if(this.selector == null)
        {
            this.selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(transportConfig.getPort()));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            new Thread(this).start();
        }
    }

    public void close(String reason) throws IOException
    {
        logger.info("Closing server : " + reason);
        if(isConnected())
            serverChannel.close();
    }

    public boolean isConnected()
    {
        return serverChannel != null && serverChannel.isOpen();
    }

    public void run()
    {
        try
        {
            while(isConnected())
            {
                this.selector.select();

                for(SelectionKey key : selector.selectedKeys())
                {
                    if(!key.isValid())
                        continue;

                    if(key.isAcceptable())
                        accept(key);
                    if(key.isReadable())
                        read(key);
                }
            }
        }
        catch (Exception e)
        {
            logger.info("Exception in server thread " + e.getMessage());
        }

    }

    private void accept(SelectionKey key) throws IOException
    {
        ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        if(clientChannel == null)
            return;

        clientChannel.configureBlocking(false);
        clientChannel.register(this.selector, SelectionKey.OP_READ);

        Transport mqttClient = new SocketTransportClient(clientChannel);
        mqttClient.connect();
        transportClientsMap.put(clientChannel, mqttClient);
    }

    public void read(SelectionKey key) throws IOException
    {
        Transport mqttClient = transportClientsMap.get(key.channel());
        if (mqttClient != null)
            mqttClient.read();
        else
            mqttClient.close("Unregistered client");
    }
}
