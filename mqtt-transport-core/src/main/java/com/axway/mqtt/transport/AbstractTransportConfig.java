package com.axway.mqtt.transport;

/**
 * Created by vchauhan on 9/21/17.
 */
public abstract class AbstractTransportConfig implements TransportConfig
{
    private TransportType transportType;
    public AbstractTransportConfig(TransportType transportType)
    {
        this.transportType = transportType;
    }

    public TransportType getTransportType() {
        return transportType;
    }
}
