/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.impl.bus;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.handler.DatagateHandler;
import org.dizitart.nitrite.datagate.bus.DatagateBus;

/**
 *
 * @author tareq
 */
public class DatagateBusMapImpl implements DatagateBus {

  private static final ConcurrentHashMap<String, HashSet<DatagateHandler>> MAP = new ConcurrentHashMap<String, HashSet<DatagateHandler>>();

  @Override
  public void subscribe(DatagateHandler session) {
    if (!StringUtils.isEmpty(session.getUser())) {
      if (MAP.get(session.getUser()) == null) {
        MAP.put(session.getUser(), new HashSet<>());
      }
      MAP.get(session.getUser()).add(session);
    }
  }

  @Override
  public void unsubscribe(DatagateHandler session) {
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
      HashSet<DatagateHandler> sessions = MAP.get(username);
      if (sessions != null) {
        sessions.parallelStream().forEach(session -> session.pushChangeList(event));
      }
    }
  }
}
