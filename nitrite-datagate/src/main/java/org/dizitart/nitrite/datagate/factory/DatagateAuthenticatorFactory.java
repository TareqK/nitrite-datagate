/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import org.dizitart.nitrite.datagate.auth.DatagateAuthenticator;
import org.dizitart.nitrite.datagate.auth.DatagateAuthenticatorDefaultImpl;

/**
 *
 * @author tareq
 */
public class DatagateAuthenticatorFactory {

  private static DatagateAuthenticatorFactory instance = getInstance();
  private static final Logger LOG = Logger.getLogger(DatagateAuthenticatorFactory.class.getName());

  private DatagateAuthenticator authenticator = new DatagateAuthenticatorDefaultImpl();

  private DatagateAuthenticatorFactory() {

  }

  public static DatagateAuthenticatorFactory getInstance() {
    if (instance == null) {
      instance = new DatagateAuthenticatorFactory();
    }
    return instance;
  }

  public DatagateAuthenticator get() {
    return authenticator;
  }

  public void setAuthenticator(Class<? extends DatagateAuthenticator> authenticatorClazz) {
    try {
      this.authenticator = authenticatorClazz.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
      LOG.severe(ex.getMessage());
      LOG.info("Authenticator class not found, defaulting to default authenticator ");
      this.authenticator = new DatagateAuthenticatorDefaultImpl();
    }
  }
}
