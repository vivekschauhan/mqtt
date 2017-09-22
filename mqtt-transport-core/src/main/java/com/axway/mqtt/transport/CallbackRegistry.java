package com.axway.mqtt.transport;

import java.util.ArrayList;

/**
 * Created by vchauhan on 9/21/17.
 */
public class CallbackRegistry
{
    private static ArrayList<TransportCallback> callbacks = new ArrayList<>();

    public final static void registerCallback(TransportCallback transportCallback)
    {
        if(!callbacks.contains(transportCallback))
            callbacks.add(transportCallback);
    }

    public final static void unregisterCallback(TransportCallback transportCallback)
    {
        if(callbacks.contains(transportCallback))
            callbacks.remove(transportCallback);
    }

    public final static ArrayList<TransportCallback> getCallbacks()
    {
        return callbacks;
    }
}
