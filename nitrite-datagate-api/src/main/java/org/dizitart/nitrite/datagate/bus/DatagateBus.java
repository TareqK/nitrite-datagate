/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.bus;

import org.dizitart.nitrite.datagate.entity.ChangeList;
import org.dizitart.nitrite.datagate.handler.DatagateHandler;

/**
 *
 * @author tareq
 */
public interface DatagateBus {

  /**
   * Subscribe this session to listen to this bus. The user property is extracted from the session, and the user's sessions are all kept in
   * a list
   *
   * @param session the session to subscribe
   */
  void subscribe(DatagateHandler session);

  /**
   * Remove this session from listening to this bus. The user property is extracted form the session, and the users session is removed from
   * that user's list
   *
   * @param session the session to unsubscribe
   */
  void unsubscribe(DatagateHandler session);

  /**
   * Publishes a changelist to a user's sessions
   *
   * @param changeList the change list to publish
   * @param username the username of the user to publish ti
   */
  void publish(ChangeList changeList, String username);

}
