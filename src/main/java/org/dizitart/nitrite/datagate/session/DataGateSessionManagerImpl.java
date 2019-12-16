/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.session;

import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author tareq
 */
public class DataGateSessionManagerImpl implements DataGateSessionManager{

    private static final Set<Session> sessions = new HashSet<>();
    private static DataGateSessionManager instance = getInstance();

    private DataGateSessionManagerImpl() {

    }

    public static DataGateSessionManager getInstance() {
        if (instance == null) {
            instance = new DataGateSessionManagerImpl();
        }
        return instance;
    }

    @Override
    public void addSession(Session session) {
        sessions.add(session);
    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session);
    }

}
