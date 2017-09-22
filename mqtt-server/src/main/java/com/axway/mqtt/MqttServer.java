package com.axway.mqtt;

import com.axway.mqtt.server.MqttSeverCallback;
import com.axway.mqtt.transport.CallbackRegistry;
import com.axway.mqtt.transport.Transport;
import com.axway.mqtt.transport.TransportServerFactory;
import com.axway.mqtt.transport.config.SocketTransportConfig;

import java.io.IOException;

/**
 * Created by vchauhan on 9/13/17.
 */
public class MqttServer
{
    public static void main(String argv[]) {
        try
        {

            CallbackRegistry.registerCallback(new MqttSeverCallback());

            System.setProperty("com.axway.mqtt.transportFactory", TransportServerFactory.class.getName());
            SocketTransportConfig socketTransportConfig= new SocketTransportConfig("localhost", 1883);
            Transport server = TransportServerFactory.create(socketTransportConfig);
            server.connect();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        server.close();
                    } catch (Exception exp) {

                    }
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
