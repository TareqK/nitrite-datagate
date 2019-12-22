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

  public boolean authenticate(DataGateSession session, String username, String password) {

    try {
      session.getSession().getUserProperties().put("username", username);
      boolean authenticated = doAuthentication(username, password);
      session.setAuthenticated(authenticated);
      return authenticated;
    } catch (Exception ex) {
      LOG.info(ex.getMessage());
      return false;
    }
  }

  abstract boolean doAuthentication(String username, String password);
}
