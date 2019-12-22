package org.dizitart.nitrite.datagate.auth;

import java.util.logging.Logger;
import org.dizitart.nitrite.datagate.session.DataGateSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
/**
 *
 * @author tareq
 */
public abstract class DataGateAuthenticator {

  private static final Logger LOG = Logger.getLogger(DataGateAuthenticator.class.getName());

  /**
   * Authenticates a user's session. If they are authenticated, then this marks the session as authenticated and inserts the username into
   * the session properties
   *
   * @param session the session to authenticate
   * @param username the username of the user
   * @param password the password of the user
   * @return whether the session was authenticated or not
   */
  public boolean authenticate(DataGateSession session, String username, String password) {

    try {
      session.getSocketSession().getUserProperties().put("username", username);
      boolean authenticated = doAuthentication(username, password);
      session.setAuthenticated(authenticated);
      return authenticated;
    } catch (Exception ex) {
      LOG.info(ex.getMessage());
      return false;
    }
  }

  /**
   * Do the authentication for the session
   *
   * @param username the username to check
   * @param password the password to check
   * @return true if the user is authenticated, false otherwise
   */
  abstract boolean doAuthentication(String username, String password);
}
