package com.axway.mqtt;

import com.axway.mqtt.core.MqttPacket;
import com.axway.mqtt.core.packet.Connect;
import com.axway.mqtt.core.packet.Connack;
import com.axway.mqtt.core.packet.ConnackReturnCodeType;
import com.axway.mqtt.core.packet.Disconnect;
import com.axway.mqtt.core.packet.Packet;
import com.axway.mqtt.core.packet.Pingreq;
import com.axway.mqtt.core.packet.Pingresp;
import com.axway.mqtt.core.packet.Puback;
import com.axway.mqtt.core.packet.Pubcomp;
import com.axway.mqtt.core.packet.Publish;
import com.axway.mqtt.core.packet.Pubrec;
import com.axway.mqtt.core.packet.Pubrel;
import com.axway.mqtt.core.packet.Suback;
import com.axway.mqtt.core.packet.Subscribe;
import com.axway.mqtt.core.packet.Unsuback;
import com.axway.mqtt.core.packet.Unsubscribe;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by vchauhan on 9/13/17.
 */
public class MqttServer
{
    static Logger logger = Logger.getLogger(MqttServer.class);

    public static void main(String argv[]) {
        try {

            testConnect();
            testConnectAck();
            testPublish();
            testPuback();
            testPubrec();
            testPubrel();
            testPubcomp();
            testSubscribe();
            testSuback();
            testUnsubscribe();
            testUnsuback();
            testPingreq();
            testPingresp();
            testDisconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void testConnect()
            throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Connect connect = new Connect();
        connect.setClientId("NewClientId");
        connect.setCleanSession(true);
        connect.setUserName("user");
        connect.setPassword("pwd");
        connect.setTopic("NewTopic");
        connect.setMessage("NewMessage");
        connect.setWillRetain(true);
        connect.setWillQos(2);
        connect.setKeepAlive(300);
        MqttPacket.writePacket(connect, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        Packet packet = MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testConnectAck() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Connack packet = new Connack();
        packet.setSessionPresent(true);
        packet.setReturnCode(ConnackReturnCodeType.REFUSED_UNACCEPTABLE_BAD_USER_PASS.getValue());
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Connack)MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testPublish() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Publish packet = new Publish();
        packet.setDup(true);
        packet.setRetain(true);
        packet.setQos(1);
        packet.setTopic("a/b");
        packet.setPacketId(10);
        packet.setPayload("ApplicationMessage".getBytes());
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Publish) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testPuback() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Puback packet = new Puback();
        packet.setPacketId(10);
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Puback) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testPubrec() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Pubrec packet = new Pubrec();
        packet.setPacketId(10);
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Pubrec) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }


    private static void testPubrel() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Pubrel packet = new Pubrel();
        packet.setPacketId(10);
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Pubrel) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testPubcomp() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Pubcomp packet = new Pubcomp();
        packet.setPacketId(10);
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Pubcomp) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testSubscribe() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Subscribe packet = new Subscribe();
        packet.setPacketId(10);
        packet.addTopicFilters("a/b", 1);
        packet.addTopicFilters("c/d", 2);
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Subscribe) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testSuback() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Suback packet = new Suback();
        packet.setPacketId(10);
        packet.addReturnCode(1);
        packet.addReturnCode( 2);
        packet.addReturnCode( 128);
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Suback) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }


    private static void testUnsubscribe() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Unsubscribe packet = new Unsubscribe();
        packet.setPacketId(10);
        packet.addTopicFilters("a/b");
        packet.addTopicFilters("c/d");
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Unsubscribe) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testUnsuback() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Unsuback packet = new Unsuback();
        packet.setPacketId(10);
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Unsuback) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testPingreq() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Pingreq packet = new Pingreq();
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Pingreq) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testPingresp() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Pingresp packet = new Pingresp();
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Pingresp) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }

    private static void testDisconnect() throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        Disconnect packet = new Disconnect();
        MqttPacket.writePacket(packet, bos);

        byte[] connectPacket = bos.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(connectPacket);
        packet = (Disconnect) MqttPacket.readPacket(in);
        packet.getMqttPacketType();
    }
}
