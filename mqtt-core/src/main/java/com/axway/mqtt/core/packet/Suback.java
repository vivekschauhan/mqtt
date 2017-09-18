package com.axway.mqtt.core.packet;

import com.axway.mqtt.core.MqttPacket;

import java.util.ArrayList;

/**
 * Created by vchauhan on 9/14/17.
 */
public class Suback extends Packet
{
    private int packetId;
    ArrayList<Integer> returnCodes = new ArrayList<>();

    public Suback() {
        super(MqttPacket.SUBACK);
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public ArrayList<Integer> getReturnCodes() {
        return returnCodes;
    }

    public void setReturnCodes(ArrayList<Integer> returnCodes) {
        this.returnCodes = returnCodes;
    }

    public void addReturnCode(int returnCode) {
        this.returnCodes.add(returnCode);
    }
}
