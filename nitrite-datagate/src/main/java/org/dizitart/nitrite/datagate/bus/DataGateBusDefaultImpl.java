/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.bus;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.handler.DataGateHandler;

/**
 *
 * @author tareq
 */
public class DataGateBusDefaultImpl implements DataGateBus {

  private static final ConcurrentHashMap<String, HashSet<DataGateHandler>> MAP = new ConcurrentHashMap<String, HashSet<DataGateHandler>>();

  @Override
  public void subscribe(DataGateHandler session) {
    if (!StringUtils.isEmpty(session.getUser())) {
      if (MAP.get(session.getUser()) == null) {
        MAP.put(session.getUser(), new HashSet<>());
      }
      MAP.get(session.getUser()).add(session);
    }
  }

  @Override
  public void unsubscribe(DataGateHandler session) {
    if (!StringUtils.isEmpty(session.getUser())) {
      if (MAP.get(session.getUser()) == null) {
        MAP.put(session.getUser(), new HashSet<>());
      }
      MAP.get(session.getUser()).remove(session);
    }
  }

  @Override
  public void publish(ChangeList event, String username) {
    if (!StringUtils.isEmpty(username)) {
      HashSet<DataGateHandler> sessions = MAP.get(username);
      if (sessions != null) {
        sessions.parallelStream().forEach(session -> session.pushChangeList(event));
      }
    }
  }

}
