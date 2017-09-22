package com.axway.mqtt.transport;

import com.axway.mqtt.transport.socket.SocketTransportClient;
import com.axway.mqtt.transport.config.SocketTransportConfig;

import java.io.IOException;

/**
 * Created by vchauhan on 9/21/17.
 */
public class TransportClientFactory
{
    public static Transport create(TransportConfig config) throws IOException
    {
        Transport transport = null;
        switch (config.getTransportType())
        {
            case MQTT:
            {
                transport = new SocketTransportClient((SocketTransportConfig)config);
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
        return transport;
    }
}
