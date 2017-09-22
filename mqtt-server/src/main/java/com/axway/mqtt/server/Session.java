package com.axway.mqtt.server;

import com.axway.mqtt.transport.Transport;

/**
 * Created by vchauhan on 9/19/17.
 */
public class Session
{
    private String clientId;
    private Transport transportClient;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Transport getTransportClient() {
        return transportClient;
    }

    public void setTransportClient(Transport transportClient) {
        this.transportClient = transportClient;
    }
}
