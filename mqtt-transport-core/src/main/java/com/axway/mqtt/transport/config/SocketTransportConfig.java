package com.axway.mqtt.transport.config;

import com.axway.mqtt.transport.AbstractTransportConfig;
import com.axway.mqtt.transport.TransportType;

/**
 * Created by vchauhan on 9/21/17.
 */
public class SocketTransportConfig extends AbstractTransportConfig
{
    private String host;
    private int port;

    public SocketTransportConfig(String host, int port)
    {
        super(TransportType.MQTT);
        this.host = host;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}
