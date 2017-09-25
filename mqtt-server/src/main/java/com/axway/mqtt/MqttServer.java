package com.axway.mqtt;

import com.axway.mqtt.server.MqttSeverCallback;
import com.axway.mqtt.transport.CallbackRegistry;
import com.axway.mqtt.transport.Transport;
import com.axway.mqtt.transport.TransportServerFactory;
import com.axway.mqtt.transport.config.SocketTransportConfig;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by vchauhan on 9/13/17.
 */
public class MqttServer
{
    private static Logger logger = Logger.getLogger(MqttServer.class);
    private static int DEFAULT_PORT = 1883;

    public static void main(String argv[]) {
        try
        {

            CallbackRegistry.registerCallback(new MqttSeverCallback());

            int port = DEFAULT_PORT;
            String mqttPort = System.getProperty("transport.mqtt.port");
            try
            {
                port = Integer.parseInt(mqttPort);
            }
            catch (NumberFormatException e)
            {
                // Use default port
            }
            SocketTransportConfig socketTransportConfig= new SocketTransportConfig("localhost", port);
            Transport server = TransportServerFactory.create(socketTransportConfig);
            server.connect();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        logger.info("Closing Server");
                        server.close("Server shutdown");
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
