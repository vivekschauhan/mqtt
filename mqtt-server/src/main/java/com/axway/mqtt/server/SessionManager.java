package com.axway.mqtt.server;

import com.axway.mqtt.transport.Transport;

import java.util.HashMap;

/**
 * Created by vchauhan on 9/19/17.
 */
public class SessionManager
{
    private HashMap<Transport, Session> sessions = new HashMap<>();
    private static SessionManager instance;

    private SessionManager() {}

    public static SessionManager instance()
    {
        if(instance == null)
            instance = new SessionManager();
        return instance;
    }

    public void addSession(Transport client, Session session)
    {
        sessions.put(client, session);
    }

    public void removeSession(Transport client)
    {
        sessions.remove(client);
    }

    public Session getSession(Transport client)
    {
        return sessions.get(client);
    }
}
