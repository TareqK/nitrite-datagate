/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import org.dizitart.nitrite.datagate.auth.DataGateAuthenticator;
import org.dizitart.nitrite.datagate.auth.DataGateAuthenticatorDefaultImpl;

/**
 *
 * @author tareq
 */
public class DataGateAuthenticatorFactory {

  private static DataGateAuthenticatorFactory instance = getInstance();
  private static final Logger LOG = Logger.getLogger(DataGateAuthenticatorFactory.class.getName());

  private DataGateAuthenticator authenticator = new DataGateAuthenticatorDefaultImpl();

  private DataGateAuthenticatorFactory() {

  }

  public static DataGateAuthenticatorFactory getInstance() {
    if (instance == null) {
      instance = new DataGateAuthenticatorFactory();
    }
    return instance;
  }

  public DataGateAuthenticator get() {
    return authenticator;
  }

  public void setAuthenticator(Class<? extends DataGateAuthenticator> authenticatorClazz) {
    try {
      this.authenticator = authenticatorClazz.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
      LOG.severe(ex.getMessage());
      LOG.info("Authenticator class not found, defaulting to default authenticator ");
      this.authenticator = new DataGateAuthenticatorDefaultImpl();
    }
  }
}
