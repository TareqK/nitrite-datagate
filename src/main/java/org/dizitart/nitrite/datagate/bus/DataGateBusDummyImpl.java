/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.bus;

import java.util.concurrent.ConcurrentHashMap;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.session.DataGateSession;

/**
 *
 * @author tareq
 */
public class DataGateBusDummyImpl implements DataGateBus{
    private static ConcurrentHashMap<String,DataGateSession> MAP = new ConcurrentHashMap<>();

    @Override
    public void subscribe(DataGateSession session) {
        MAP.put(session.getSessionUser(), session);
    }

    @Override
    public void unsubscribe(DataGateSession session) {
        MAP.remove(session.getSessionUser());
    }

    @Override
    public void publish(ChangeList event, String username) {
        DataGateSession session = MAP.get(username);
        if(session!=null){
            session.push(event);
        }
    }
    
}
