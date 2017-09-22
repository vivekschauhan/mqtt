package com.axway.mqtt.transport;

import com.axway.mqtt.transport.config.SocketTransportConfig;
import com.axway.mqtt.transport.socket.SocketTransportServer;

import java.io.IOException;

/**
 * Created by vchauhan on 9/21/17.
 */
public class TransportServerFactory
{
    public static Transport create(TransportConfig config) throws IOException
    {
        Transport transportServer = null;
        switch (config.getTransportType())
        {
            case MQTT:
            {
                transportServer = new SocketTransportServer((SocketTransportConfig)config);
                break;
            }
            case MQTT_TLS:
            {
                break;
            }
            case WEBSOCKET:
            {
                break;
            }
            default:
                throw new IOException("Unexpected transport type");
        }
        return transportServer;
    }
}
