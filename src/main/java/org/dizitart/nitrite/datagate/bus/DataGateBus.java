/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.bus;

import javax.websocket.Session;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.session.DataGateSession;

/**
 *
 * @author tareq
 */
public interface DataGateBus {
    
    void subscribe(DataGateSession session);
    
    void unsubscribe(DataGateSession session);
    
    void publish(ChangeList event, String username);
    
}
