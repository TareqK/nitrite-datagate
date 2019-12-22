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
import org.dizitart.nitrite.datagate.session.DataGateSession;

/**
 *
 * @author tareq
 */
public class DataGateBusDefaultImpl implements DataGateBus {

  private static final ConcurrentHashMap<String, HashSet<DataGateSession>> MAP = new ConcurrentHashMap<String, HashSet<DataGateSession>>();

  @Override
  public void subscribe(DataGateSession session) {
    if (!StringUtils.isEmpty(session.getSessionUser())) {
      if (MAP.get(session.getSessionUser()) == null) {
        MAP.put(session.getSessionUser(), new HashSet<>());
      }
      MAP.get(session.getSessionUser()).add(session);
    }
  }

  @Override
  public void unsubscribe(DataGateSession session) {
    if (!StringUtils.isEmpty(session.getSessionUser())) {
      if (MAP.get(session.getSessionUser()) == null) {
        MAP.put(session.getSessionUser(), new HashSet<>());
      }
      MAP.get(session.getSessionUser()).remove(session);
    }
  }

  @Override
  public void publish(ChangeList event, String username) {
    if (!StringUtils.isEmpty(username)) {
      HashSet<DataGateSession> sessions = MAP.get(username);
      if (sessions != null) {
        sessions.parallelStream().forEach(session -> session.pushChangeList(event));
      }
    }
  }

}
