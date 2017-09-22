package com.axway.mqtt.server;

import com.axway.mqtt.core.packet.Connect;
import com.axway.mqtt.core.packet.Connack;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pingresp;
import com.axway.mqtt.core.packet.Puback;
import com.axway.mqtt.core.packet.Pubcomp;
import com.axway.mqtt.core.packet.Publish;
import com.axway.mqtt.core.packet.Pubrec;
import com.axway.mqtt.core.packet.Pubrel;
import com.axway.mqtt.core.packet.Suback;
import com.axway.mqtt.core.packet.Subscribe;
import com.axway.mqtt.core.packet.SubscribeTopicFilter;
import com.axway.mqtt.core.packet.Unsuback;
import com.axway.mqtt.core.packet.Unsubscribe;
import com.axway.mqtt.transport.TransportCallback;
import com.axway.mqtt.transport.Transport;
import com.axway.mqtt.transport.socket.SocketTransportClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vchauhan on 9/19/17.
 */
public class MqttSeverCallback implements TransportCallback
{
    static Logger logger = Logger.getLogger(SocketTransportClient.class);

    public void onPacketReceive(Packet packet, Transport client)
    {
        if(packet == null)
            return;

        try {
            switch (packet.getMqttPacketType()) {
                case CONNECT: {
                    Connect connect = (Connect) packet;

                    Session session = new Session();
                    session.setClientId(connect.getClientId());
                    session.setTransportClient(client);
                    SessionManager.instance().addSession(client, session);

                    Connack connack = new Connack();
                    connack.setSessionPresent(false);
                    connack.setReturnCode(0);
                    client.write(connack);
                    break;
                }
                case PUBLISH: {
                    Publish publish = (Publish) packet;
                    if(publish.getQos() == 1)
                    {
                        Puback puback = new Puback();
                        puback.setPacketId(publish.getPacketId());
                        client.write(puback);
                    }
                    else if(publish.getQos() == 2)
                    {
                        Pubrec pubrec = new Pubrec();
                        pubrec.setPacketId(publish.getPacketId());
                        client.write(pubrec);
                    }

                    ArrayList<Session> sessions = SubscriptionStore.instance().getSubscribedSessions(publish.getTopic());
                    if (sessions != null) {
                        for (Session session : sessions) {
                            if (session.getTransportClient().isConnected()) {
                                Publish clientPublish = new Publish();
                                clientPublish.setQos(publish.getQos());
                                if(clientPublish.getQos() == 0)
                                    clientPublish.setDup(false);
                                else
                                    clientPublish.setDup(publish.isDup()); // To be changed - based on attempt
                                clientPublish.setRetain(publish.isRetain());
                                clientPublish.setTopic(publish.getTopic());
                                clientPublish.setPacketId(publish.getPacketId());
                                clientPublish.setPayload(publish.getPayload());
                                session.getTransportClient().write(clientPublish);
                            }
                        }
                    }
                    break;
                }
                case PUBACK: {
                    // Process Puback - for QoS 1 - remove publish
                    break;
                }
                case PUBREC: {
                    Pubrec pubrec = (Pubrec) packet;
                    Pubrel pubrel = new Pubrel();
                    pubrel.setPacketId(pubrec.getPacketId());
                    client.write(pubrel);
                    // Process Pubrel message
                    break;
                }
                case PUBREL: {
                    Pubrel pubrel = (Pubrel) packet;
                    Pubcomp pubcomp = new Pubcomp();
                    pubcomp.setPacketId(pubrel.getPacketId());
                    client.write(pubcomp);
                    // Process Pubrec message - For Qos 2 - remove publish
                    break;
                }
                case PUBCOMP: {
                    Pubcomp pubcomp = (Pubcomp) packet;
                    // Process Pubcomp message
                    break;
                }
                case SUBSCRIBE: {
                    Subscribe subscribe = (Subscribe) packet;
                    Suback suback = new Suback();
                    suback.setPacketId(subscribe.getPacketId());
                    suback.addReturnCode(1);
                    client.write(suback);

                    Session session = SessionManager.instance().getSession(client);
                    if (session != null) {
                        for (SubscribeTopicFilter topic : subscribe.getTopicFilters()) {
                            SubscriptionStore.instance().addSubscription(topic.getTopicFilter(), session);
                        }
                    }
                    break;
                }
                case UNSUBSCRIBE: {
                    Unsubscribe unsubscribe = (Unsubscribe) packet;
                    Unsuback unsuback = new Unsuback();
                    unsuback.setPacketId(unsubscribe.getPacketId());
                    client.write(unsuback);

                    for (SubscribeTopicFilter topic : unsubscribe.getTopicFilters()) {
                        SubscriptionStore.instance().removeSubscription(topic.getTopicFilter());
                    }
                    break;
                }
                case PINGREQ: {
                    client.write(new Pingresp());
                    break;
                }
                case DISCONNECT: {
                    SessionManager.instance().removeSession(client);
                    client.close();
                    break;
                }
            }
        }
        catch (IOException e)
        {
            logger.info(e.getMessage());
        }
    }

    public void onPacketSend(Packet packet, Transport client)
    {

        if(packet == null)
            return;

        try {
            switch (packet.getMqttPacketType()) {
                case CONNACK: {
                    Connack connack = (Connack) packet;

                    break;
                }
                case PUBLISH: {
                    Publish publish = (Publish) packet;
                    // Manage Qos
                    break;
                }
                case PUBACK: {
                    // Process Puback - for QoS 1 - remove publish
                    break;
                }
                case PUBREC: {
                    // Process Pubrel message
                    break;
                }
                case PUBREL: {
                    // Process Pubrec message - For Qos 2 - remove publish
                    break;
                }
                case PUBCOMP: {
                    Pubcomp pubcomp = (Pubcomp) packet;
                    // Process Pubcomp message
                    break;
                }
                case SUBACK: {
                    Suback suback = (Suback) packet;

                    break;
                }
                case UNSUBSCRIBE: {
                    Unsubscribe unsubscribe = (Unsubscribe) packet;
                    Unsuback unsuback = new Unsuback();
                    unsuback.setPacketId(unsubscribe.getPacketId());
                    client.write(unsuback);

                    for (SubscribeTopicFilter topic : unsubscribe.getTopicFilters()) {
                        SubscriptionStore.instance().removeSubscription(topic.getTopicFilter());
                    }
                    break;
                }
                case PINGREQ: {
                    client.write(new Pingresp());
                    break;
                }
                case DISCONNECT: {
                    SessionManager.instance().removeSession(client);
                    client.close();
                    break;
                }
            }
        }
        catch (IOException e)
        {
            logger.info(e.getMessage());
        }

    }
}
