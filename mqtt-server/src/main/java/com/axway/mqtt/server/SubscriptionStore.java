package com.axway.mqtt.server;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vchauhan on 9/19/17.
 */
public class SubscriptionStore
{
    private HashMap<String, ArrayList<Session>> subscriptions = new HashMap<>();
    private static SubscriptionStore instance;

    private SubscriptionStore()
    {}

    public static SubscriptionStore instance()
    {
        if(instance == null)
            instance = new SubscriptionStore();
        return instance;
    }

    public void addSubscription(String topic, Session session)
    {
        ArrayList<Session> subscribedSessions = subscriptions.get(topic);
        if(subscribedSessions == null)
            subscribedSessions = new ArrayList<>();
        subscribedSessions.add(session);
        subscriptions.put(topic, subscribedSessions);
    }

    public ArrayList<Session> getSubscribedSessions(String topic)
    {
        return subscriptions.get(topic);
    }

    public void removeSubscription(String topic)
    {
        subscriptions.remove(topic);
    }
}
